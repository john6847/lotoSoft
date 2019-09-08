package com.b.r.loteriab.r.Notification.Jobs;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Enums.SaleSatus;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Notification.Service.AuditEventServiceImpl;
import com.b.r.loteriab.r.Repository.*;
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

   @Autowired
   private SaleDetailRepository saleDetailRepository;

   @Autowired
   private TicketRepository ticketRepository;

   @Autowired
   private CombinationRepository combinationRepository;

    /**
     * Schedule to send notification to enable and disable shift every 10 seconds.
     * @return configuration
     */
   @Scheduled(fixedRate = 60000)
   public void webSocketPing() {
       System.out.println("Web socket Ping");
       for (Map.Entry<Integer, LastNotification> entry : AuditEventServiceImpl.lastNotificationMap.entrySet())
       {
           long diff = new Date().getTime() - entry.getValue().getDate().getTime();
           long diffMinutes = diff / (60 * 1000) % 150;
           if (diffMinutes <= 120L){

               System.out.println("There Number");
               if(entry.getValue().isChanged()) {
                   if (entry.getKey() == NotificationType.CombinationBlocked.ordinal()){
                       System.out.println("Combination Bloked");
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
    /**
     * Schedule to send notification to enable and disable shift every 10 seconds.
     * @return configuration
     */
    @Scheduled(fixedRate = 100000)
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
                              //  deleteIncompleteSale(entry.getKey());
                                deleteNotificationInList(shift.getId()); // Delete old (combination limit price) notification
                                setSaleTotalBackToDefault(entry.getKey());
                                shiftRepository.save(shift);
                                Shift other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), entry.getKey());
                                other.setEnabled(true);
                                shiftRepository.save(other);
                            }
                        } else {
                            date = Helper.addDays(date, 1);
                        }
                        sendCombinationPriceLimitNotif();
                        System.out.println("Close date " + date);
                        System.out.println("Actual date " + new Date());
                    }
                }
                if (shift.getName().equals(Shifts.New_York.name()) && shift.isEnabled()) {
                    if(!shift.getCloseTime().isEmpty()){
                        Date date = Helper.getCloseDateTime(shift.getCloseTime(), new Date());
                        if (new Date().after(date)){
                            shift.setEnabled(false);
                           //deleteIncompleteSale(entry.getKey());
                            setSaleTotalBackToDefault(entry.getKey()); // Delete old (combination limit price) notification
                            deleteNotificationInList(shift.getId());
                            shiftRepository.save(shift);
                            Shift other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), entry.getKey());
                            other.setEnabled(true);
                            shiftRepository.save(other);
                        }
                        System.out.println("Close date "+ date);
                        System.out.println("Actual date "+ new Date());
                        sendCombinationPriceLimitNotif();
                    }
                }
            }
        }
    }

/**
 * Method to delete notification that are at the price limit for a shift long
 * @param currentShiftId
 * @return configuration
 */
    private void deleteNotificationInList(Long currentShiftId) {
        List<LastNotification> lastNotifications = AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal());
        if (lastNotifications != null){
            for (LastNotification lastNotification : lastNotifications){
                if (lastNotification.isChanged()) {
                    Long shiftId = (Long) lastNotification.getSampleResponse().getBody().get("shiftId");
                    if (shiftId!= null && shiftId.equals(currentShiftId)){
                        AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal()).remove(lastNotification);
                    }
                }
            }
        }
    }

    /**
     * Method to set combination saleTotal back to the defauult value
     * @return configuration
     */
    private  void setSaleTotalBackToDefault(Long enterpriseId){
        combinationRepository.updateCombinationSaleTotal(enterpriseId);
    }

    /**
     * Method to send notification to admin about combination that are at the price limit
     * @return configuration
     */
    private  void sendCombinationPriceLimitNotif(){
        List<LastNotification> lastNotifications = AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal());
        if (lastNotifications != null){
            for (LastNotification lastNotification : lastNotifications){
                if (lastNotification.isChanged()) {
                    service.sendMessage(lastNotification.getSampleResponse(), lastNotification.getEnterpriseId(), null);
                }
            }
        }
    }

    private void deleteIncompleteSale(Long enterpriseId) {
       List<Sale> sales = saleRepository.findAllByEnterpriseId(enterpriseId);
       for (Sale sale: sales){
           if (sale.getSaleStatus() == SaleSatus.SAVING.ordinal()){
//               for (SaleDetail saleDetail: sale.getSaleDetails()){
//                    saleDetailRepository.deleteByIdAndEnterpriseId(saleDetail.getId(), enterpriseId);
//               }
               sale.setEnterprise(null);
               sale.setShift(null);
               Long ticketId = sale.getTicket().getId();
               sale.setSeller(null);
               sale.setPos(null);
               sale.setTicket(null);
               saleRepository.save(sale);

               Ticket ticket = ticketRepository.findTicketByIdAndEnterpriseId(ticketId, enterpriseId);
               ticket.setEnterprise(null);
               ticketRepository.save(ticket);
               ticketRepository.deleteByIdAndEnterpriseId(enterpriseId, ticketId);
               saleRepository.deleteSaleByIdAndEnterpriseId(sale.getId(), sale.getEnterprise().getId());
           }
       }
     }

    /**
     * Schedule to send notification about the server time
     * @return configuration
     */
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
