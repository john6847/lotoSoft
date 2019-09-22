package com.b.r.loteriab.r.Notification.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.b.r.loteriab.r.Model.AuditEvent;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Validation.Helper;
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
    private Map<Long, ConcurrentLinkedQueue<SampleResponse>> eventQueue = new HashMap<>();
    public static Map<Integer, LastNotification> lastNotificationMap = new HashMap<>();
    public static Map<Integer, ArrayList<LastNotification>> lastNotificationMapList = new HashMap<>();
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @Override
    public void sendMessage(SampleResponse sampleResponse, Long enterpriseId, LastNotification last) {
        enqueue(sampleResponse, enterpriseId);

        if (last != null && last.getType() > -1 && last.getType() != NotificationType.SendSystemDate.ordinal()) {
            AuditEventServiceImpl.lastNotificationMap.put(last.getType(), last);
        }

        if (last!=null && last.getType() > -1 && last.getType() != NotificationType.CombinationPriceLimit.ordinal()){
            List <LastNotification> existedList = AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal());
            if (existedList != null) {
                AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal()).add(last);
            } else {
                AuditEventServiceImpl.lastNotificationMapList
                        .put(NotificationType.CombinationPriceLimit.ordinal(), AuditEventServiceImpl.lastNotificationMapList.getOrDefault( NotificationType.CombinationPriceLimit.ordinal(), new ArrayList<>(Arrays.asList(last))));
            }
        }

        if (last != null && last.getType() > 0 && (last.getType() == NotificationType.PosBlocked.ordinal()
                || last.getType() == NotificationType.CombinationPriceLimit.ordinal()
                || last.getType() == NotificationType.CombinationBlocked.ordinal()
                || last.getType() == NotificationType.TopSoldCombination.ordinal()
                || last.getType() == NotificationType.UserConnected.ordinal())) {
            brokerMessagingTemplate.convertAndSend("/topics/" + enterpriseId.toString() + "/" + last.getType() + "/event", JSON.toJSONString(sampleResponse, SerializerFeature.BrowserCompatible));
        }

        if (last != null  && last.getType() > 0 && last.getType() == NotificationType.SendSystemDate.ordinal()) {
            brokerMessagingTemplate.convertAndSend("/topics/time", JSON.toJSONString(Helper.getSystemDate(), SerializerFeature.BrowserCompatible));
        }
        brokerMessagingTemplate.convertAndSend("/topics/" + enterpriseId + "/event", JSON.toJSONString(sampleResponse, SerializerFeature.BrowserCompatible,SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect));
    }

    private void enqueue(SampleResponse sampleResponse, Long enterpriseId) {
        eventQueue.put(enterpriseId, eventQueue.getOrDefault(enterpriseId, new ConcurrentLinkedQueue<>((Arrays.asList(sampleResponse)))));

        if (eventQueue.size() > 300) {
            eventQueue.get(enterpriseId).remove();
        }
    }
}
