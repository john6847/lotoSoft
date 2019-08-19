package com.b.r.loteriab.r.Notification.Jobs;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Notification.Service.AuditEventServiceImpl;
import com.b.r.loteriab.r.Repository.CombinationRepository;
import com.b.r.loteriab.r.Repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


/**
 * Created by xschen on 16/9/2017.
 *
 * A simple scheduler that tries to ping the web client every 10 seconds via web-socket
 */
@Component
public class WebSocketPingScheduler {

   @Autowired
   private AuditEventService service;

   @Autowired
   private CombinationRepository combinationRepository;

   @Autowired
   private ShiftRepository shiftRepository;

   @Scheduled(fixedRate = 60000)
   public void webSocketPing() {
       System.out.println("There");
       for (Map.Entry<Integer, LastNotification> entry : AuditEventServiceImpl.lastNotificationMap.entrySet())
       {
           long diff = new Date().getTime() - entry.getValue().getDate().getTime();
           long diffMinutes = diff / (60 * 1000) % 150;
           if (diffMinutes <= 120L){
               if(entry.getValue().isChanged()) {
                   if (entry.getKey() == NotificationType.CombinationBlocked.ordinal()){
                       service.sendMessage(entry.getValue().getSampleResponse(), entry.getValue().getEnterpriseId(), null);
                   }
               } else if (entry.getKey() == NotificationType.ShiftChange.ordinal()) {
                   service.sendMessage(entry.getValue().getSampleResponse(), entry.getValue().getEnterpriseId(), null);
               } else if (entry.getKey() == NotificationType.PosBlocked.ordinal()) {
                   service.sendMessage(entry.getValue().getSampleResponse(), entry.getValue().getEnterpriseId(), null);
               }
           } else {
               AuditEventServiceImpl.lastNotificationMap.get(entry.getKey()).setChanged(false);
           }

       }
   }

    @Scheduled(fixedRate = 10000)
    public void enableAndDisableShift() {
//        ArrayList<Shift> shiftList = (ArrayList<Shift>) shiftRepository.findAll();
//        Map<Long, List<Shift>> mapShifts = shiftList.stream()
//                .collect(groupingBy(Enterprise, toList()));
//
//        for (Shift shift: shiftList) {
//            mapShifts.put(shift.getEnterprise().getId(), mapShifts.getOrDefault(shift.getEnterprise().getId(), new ArrayList<>())
//        }
    }
}
