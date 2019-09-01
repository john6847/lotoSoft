package com.b.r.loteriab.r.Notification.Jobs;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.SaleSatus;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.Sale;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Notification.Service.AuditEventServiceImpl;
import com.b.r.loteriab.r.Repository.CombinationRepository;
import com.b.r.loteriab.r.Repository.EnterpriseRepository;
import com.b.r.loteriab.r.Repository.SaleRepository;
import com.b.r.loteriab.r.Repository.ShiftRepository;
import com.b.r.loteriab.r.Validation.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
 * Created by Dany on 16/9/2017.
 *
 * A simple scheduler that tries to ping the web client every 10 seconds via web-socket
 */
@Component
public class WebSocketPingScheduler {

   @Autowired
   private AuditEventService service;

   @Autowired
   private ShiftRepository shiftRepository;

   @Autowired
   private SaleRepository saleRepository;

   @Scheduled(fixedRate = 60000)
   public void webSocketPing() {
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
        ArrayList<Shift> shiftList = (ArrayList<Shift>) shiftRepository.findAll();
        Map<Long, List<Shift>> mapShifts = shiftList.stream()
                .collect(groupingBy(o -> o.getEnterprise().getId(), mapping((Shift s) -> s, toList())));

        for (Map.Entry<Long, List<Shift>> entry : mapShifts.entrySet()){
            for (Shift shift : entry.getValue()){
                System.out.println("there");
                if(shift.getName().equals(Shifts.Maten.name()) && shift.isEnabled()) {
                    if (!shift.getCloseTime().isEmpty()) {
                        Date date = Helper.getCloseDateTime(shift.getCloseTime(), new Date());
                        int hour = Helper.getTimeValueFromDate(new Date(), 1);
                        if (hour < 20) {
                            if (new Date().after(date)) {
                                shift.setEnabled(false);
                                shiftRepository.save(shift);
                                //deleteIncompleteSale(entry.getKey());
                                Shift other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), entry.getKey());
                                other.setEnabled(true);
                                shiftRepository.save(other);
                            }
                        } else {
                            date = Helper.addDays(date, 1);
                        }
                        System.out.println("Close date " + date);
                        System.out.println("Actual date " + new Date());
                    }
                }
                if (shift.getName().equals(Shifts.New_York.name()) && shift.isEnabled()) {
                    if(!shift.getCloseTime().isEmpty()){
                        Date date = Helper.getCloseDateTime(shift.getCloseTime(), new Date());
                        if (new Date().after(date)){
                            shift.setEnabled(false);
                            shiftRepository.save(shift);
                           //deleteIncompleteSale(entry.getKey());
                            Shift other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), entry.getKey());
                            other.setEnabled(true);
                            shiftRepository.save(other);
                        }
                        System.out.println("Close date "+ date);
                        System.out.println("Actual date "+ new Date());
                    }
                }
            }
        }
    }

    private void deleteIncompleteSale(Long enterpriseId) {
       List<Sale> sales = saleRepository.findAllByEnterpriseId(enterpriseId);
       for (Sale sale: sales){
           if (sale.getSaleStatus() == SaleSatus.SAVING.ordinal()){
               saleRepository.deleteSaleByIdAndEnterpriseId(sale.getId(), sale.getEnterprise().getId());
           }
       }
     }

    @Scheduled(fixedRate = 1000)
    public void sendSystemDate() {
       SampleResponse sampleResponse = new SampleResponse();
       sampleResponse.setBody(new HashMap());

       LastNotification last = new LastNotification();
       last.setChanged(false);
       last.setDate(new Date());
       last.setType(NotificationType.SendSystemDate.ordinal());

        sampleResponse.getBody().put("date", Helper.getSystemDate());
        service.sendMessage(sampleResponse, 0L, last);
    }
}
