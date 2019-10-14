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
import com.b.r.loteriab.r.Services.TokenService;
import com.b.r.loteriab.r.Validation.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.*;


/**
 * Created by Dany on 16/9/2017.
 * <p>
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
    private TicketRepository ticketRepository;

    @Autowired
    private CombinationRepository combinationRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private  SaleDetailRepository saleDetailRepository;

    @Autowired
    private CombinationHistoryRepository combinationHistoryRepository;

    /**
     * Schedule to send notification to enable and disable shift every 10 seconds.
     *
     * @return configuration
     */
    @Scheduled(fixedRate = 60000)
    public void webSocketPing() {
        System.out.println("Web socket Ping");
        for (Map.Entry<Integer, LastNotification> entry : AuditEventServiceImpl.lastNotificationMap.entrySet()) {
            long diff = new Date().getTime() - entry.getValue().getDate().getTime();
            long diffMinutes = diff / (60 * 1000) % 150;
            if (diffMinutes <= 120L) {

                System.out.println("There Number");
                if (entry.getValue().isChanged()) {
                    if (entry.getKey() == NotificationType.CombinationBlocked.ordinal()) {
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
     *
     * @return configuration
     */
    @Scheduled(fixedRate = 10000)
    public void enableAndDisableShift() {
        ArrayList<Shift> shiftList = (ArrayList<Shift>) shiftRepository.findAll();
        Map<Long, List<Shift>> mapShifts = shiftList.stream()
                .collect(groupingBy(o -> o.getEnterprise().getId(), mapping((Shift s) -> s, toList())));

        for (Map.Entry<Long, List<Shift>> entry : mapShifts.entrySet()) {
            for (Shift shift : entry.getValue()) {
                if (shift.getName().equals(Shifts.Maten.name()) && shift.isEnabled()) {
                    if (!shift.getCloseTime().isEmpty()) {
                        Date date = Helper.getCloseDateTime(shift.getCloseTime(), new Date());
                        int hour = Helper.getTimeValueFromDate(new Date(), 1);
                        if (hour < 20) {
                            if (new Date().after(date)) {
                                shift.setEnabled(false);
                                shiftRepository.save(shift);
                                Shift other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), entry.getKey());
                                other.setEnabled(true);
                                shiftRepository.save(other);
                                // other configuration
                                setSaleTotalBackToDefault(entry.getKey());
                                deleteNotificationInList(shift.getId()); // Delete old (combination limit price) notification
                                deleteIncompleteSale(entry.getKey());
                                cleanLoggedInUser(entry.getKey());
                                createCombinationHistory(entry.getKey(), shift.getId());
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
                    if (!shift.getCloseTime().isEmpty()) {
                        Date date = Helper.getCloseDateTime(shift.getCloseTime(), new Date());
                        if (new Date().after(date)) {
                            shift.setEnabled(false);
                            shiftRepository.save(shift);
                            Shift other = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), entry.getKey());
                            other.setEnabled(true);
                            shiftRepository.save(other);
                            // other configuration
                            deleteIncompleteSale(entry.getKey());
                            setSaleTotalBackToDefault(entry.getKey()); // Delete old (combination limit price) notification
                            deleteNotificationInList(shift.getId());
                            cleanLoggedInUser(entry.getKey());
                            createCombinationHistory(entry.getKey(), shift.getId());
                        }
                        System.out.println("Close date " + date);
                        System.out.println("Actual date " + new Date());
                        sendCombinationPriceLimitNotif();
                    }
                }
            }
        }
    }

    /**
     * Method to delete notification that are at the price limit for a shift long
     *
     * @param currentShiftId
     * @return configuration
     */
    private void deleteNotificationInList(Long currentShiftId) {
        List<LastNotification> lastNotifications = AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal());
        if (lastNotifications != null) {
            for (LastNotification lastNotification : lastNotifications) {
                if (lastNotification.isChanged()) {
                    Long shiftId = (Long) lastNotification.getSampleResponse().getBody().get("shiftId");
                    if (shiftId != null && shiftId.equals(currentShiftId)) {
                        AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal()).remove(lastNotification);
                    }
                }
            }
        }
    }

    /**
     * Schedule to send notification about the server time
     *
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

    /**
     * Schedule to send notification about the server time
     *
     * @return configuration
     */
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void sendTop3SoldCombinationByCombinationType() {
        List<Enterprise> enterprises = enterpriseRepository.selectAllEnterpriseExcept("BR-tenant");
        for (Enterprise enterprise : enterprises) {
            SampleResponse sampleResponse = new SampleResponse();
            sampleResponse.setBody(new HashMap());

            LastNotification last = new LastNotification();
            last.setChanged(false);
            last.setDate(new Date());
            last.setEnterpriseId(enterprise.getId());
            last.setType(NotificationType.TopSoldCombination.ordinal());

            sampleResponse.getBody().put("combinations", combinationRepository.selectTop3MostSoldCombinationByCombinationType(enterprise.getId(), Combination.class));
            service.sendMessage(sampleResponse, enterprise.getId(), last);
        }

    }

    /**
     * Method to set combination saleTotal back to the defauult value
     *
     * @return
     */
    private void setSaleTotalBackToDefault(Long enterpriseId) {
        combinationRepository.updateCombinationSaleTotal(enterpriseId);
    }

    /**
     * Method to send notification to admin about combination that are at the price limit
     *
     * @return
     */
    private void sendCombinationPriceLimitNotif() {
        List<LastNotification> lastNotifications = AuditEventServiceImpl.lastNotificationMapList.get(NotificationType.CombinationPriceLimit.ordinal());
        if (lastNotifications != null) {
            for (LastNotification lastNotification : lastNotifications) {
                if (lastNotification.isChanged()) {
                    service.sendMessage(lastNotification.getSampleResponse(), lastNotification.getEnterpriseId(), null);
                }
            }
        }
    }

    /**
     * Method to clean oall the logged in user
     *
     * @return
     */
    private void cleanLoggedInUser(Long enterpriseId) {
        TokenService.removeTokens(enterpriseId);
    }

    private void deleteIncompleteSale(Long enterpriseId) {
        List<Sale> sales = saleRepository.findAllByEnterpriseId(enterpriseId);
        for (Sale sale : sales) {
            if (sale.getSaleStatus() == SaleSatus.SAVING.ordinal()) {
                sale.setEnterprise(null);
                sale.setShift(null);
                Ticket ticket = ticketRepository.findTicketByIdAndEnterpriseId(sale.getTicket().getId(), enterpriseId);
                ticket.setDeleted(true);
                ticket.setShift(null);
                ticket.setEnterprise(null);
                ticketRepository.save(ticket);
                sale.setSeller(null);
                sale.setPos(null);
                sale.setDeleted(true);
                sale.setTicket(null);
                for (SaleDetail saleDetail: sale.getSaleDetails()) {
                    Combination combination = combinationRepository.findCombinationById(saleDetail.getCombination().getId());
                    combination.setSaleTotal(combination.getSaleTotal() - saleDetail.getPrice());
                    combinationRepository.save(combination);
                    SaleDetail savedSaleDetail = saleDetailRepository.findByEnterpriseIdAndId(enterpriseId,saleDetail.getId());
                    saleDetail.setDeleted(true);
                    saleDetailRepository.save(savedSaleDetail);
                }
                sale.setSaleDetails(null);
                saleRepository.save(sale);
            }
        }
    }

    private void createCombinationHistory(Long enterpriseId, Long shiftId) {
        Enterprise enterprise = enterpriseRepository.findEnterpriseById(enterpriseId);
        if (enterprise == null)
            return;
        Shift shift = shiftRepository.findShiftByIdAndEnterpriseId(shiftId, enterpriseId);
        if (shift == null)
            return;
        List <Combination> combinations = combinationRepository.findAllByEnterpriseIdAndSaleTotalGreaterThan(enterpriseId, 0, Combination.class);
        for (Combination combination : combinations){
            CombinationHistory combinationHistory = new CombinationHistory();
            combinationHistory.setSaleTotal(combination.getSaleTotal());
            combinationHistory.setEnterprise(enterprise);
            combinationHistory.setCombination(combination);
            combinationHistory.setShift(shift);
            combinationHistoryRepository.save(combinationHistory);
        }
    }
}
