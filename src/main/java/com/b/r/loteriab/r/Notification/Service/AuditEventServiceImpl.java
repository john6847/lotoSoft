package com.b.r.loteriab.r.Notification.Service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.b.r.loteriab.r.Model.AuditEvent;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by John on 18/9/2017.
 */
@Service
public class AuditEventServiceImpl implements AuditEventService {
   private ConcurrentMap<String, Long> histogram = new ConcurrentHashMap<>();
   private Map <Long, ConcurrentLinkedQueue<SampleResponse>> eventQueue = new HashMap<>();
   public static Map<Integer, LastNotification> lastNotificationMap = new HashMap<>();
   @Autowired
   private SimpMessagingTemplate brokerMessagingTemplate;

   @Override
   public void sendMessage(SampleResponse sampleResponse, Long enterpriseId, LastNotification last) {
      enqueue(sampleResponse, enterpriseId);

      if(last != null){
         AuditEventServiceImpl.lastNotificationMap.put(last.getType(), last);
      }

//      histogram.put(category, histogram.getOrDefault(category, 0L) + 1);
      brokerMessagingTemplate.convertAndSend("/topics/"+enterpriseId+"/event", JSON.toJSONString(sampleResponse, SerializerFeature.BrowserCompatible));
   }

   private void enqueue(SampleResponse sampleResponse, Long enterpriseId) {
       eventQueue.put(enterpriseId, eventQueue.getOrDefault(enterpriseId, new ConcurrentLinkedQueue<SampleResponse>((Arrays.asList(sampleResponse)))));

      if(eventQueue.size() > 300) {
         eventQueue.get(enterpriseId).remove();
      }
   }
//   @Override public AuditEvent getLastEvent() {
//      return lastEvent;
//   }
//
//
//   @Override public Map<String, Long> getEventCountHistogram() {
//      return histogram;
//   }
//
//   @Override public long getCounter() {
//      return counter;
//   }
//
//
//   @Override public List<AuditEvent> getLastEvents() {
//      return new ArrayList<>(eventQueue);
//   }
}
