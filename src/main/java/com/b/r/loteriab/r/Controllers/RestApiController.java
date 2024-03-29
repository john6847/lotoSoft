package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Enums.CombinationTypes;
import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.ViewModel.SaleViewModel;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Model.ViewModel.TicketWonViewModel;
import com.b.r.loteriab.r.Model.ViewModel.UserViewModel;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Repository.*;
import com.b.r.loteriab.r.Services.ApiService;
import com.b.r.loteriab.r.Services.GlobalConfigurationService;
import com.b.r.loteriab.r.Services.TokenService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.GlobalHelper;
import com.b.r.loteriab.r.Validation.Helper;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */
@RestController
@RequestMapping("/deadRouteBR")
public class RestApiController {

    private static final String ACCEPT_TYPE = "application/json";
    @Autowired
    private SaleDetailRepository saleDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PosRepository posRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private CombinationRepository combinationRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ApiService apiService;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CombinationTypeRepository combinationTypeRepository;
    @Autowired
    private DrawRepository drawRepository;
    @Autowired
    private GlobalHelper globalHelper;
    @Autowired
    private AuditEventService auditService;
    @Autowired
    private GlobalConfigurationService globalConfigurationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> authenticate(@RequestBody UserViewModel vm) {
        SampleResponse sampleResponse = new SampleResponse();

        if (vm.getEnterpriseName() == null || vm.getEnterpriseName().isEmpty()) {
            sampleResponse.setMessage("Ou sipoze voye non antrepriz la");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        if (vm.getUsername() == null || vm.getUsername().isEmpty()) {
            sampleResponse.setMessage("Ou sipoze antre non ititilizate ou a");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        if (vm.getPassword() == null || vm.getPassword().isEmpty()) {
            sampleResponse.setMessage("Ou sipoze antre modpas ou a");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        Enterprise enterprise = enterpriseRepository.findEnterpriseByEnabledAndNameIgnoreCase(true, vm.getEnterpriseName());
        if (enterprise == null) {
            sampleResponse.setMessage("Non antrepriz la pa bon reeseye avek yon lot non pou ou ka konekte");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }
        Pos pos = posRepository.findBySerialAndEnabledAndEnterpriseIdAndDeletedFalse(vm.getSerial(), true, enterprise.getId());
        if (pos == null) {
            sampleResponse.setMessage("Machin sa pa gen pèmisyon konekte");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }


        Users user = userRepository.findByUsernameAndEnterpriseId(vm.getUsername(), enterprise.getId());
        if (user == null) {
            sampleResponse.setMessage("Itilizatè sa pa egziste");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        Seller seller = sellerRepository.findByUserIdAndDeletedFalseAndEnterpriseId(user.getId(), enterprise.getId());
        if (seller == null) {
            sampleResponse.setMessage("Vandè sa pa egziste");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        } else {
            if (!seller.isEnabled()) {
                sampleResponse.setMessage("Vandè sa pa gen pèmisyon konekte");
                sampleResponse.getBody().put("ok", false);
                return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
            }
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(vm.getPassword(), user.getPassword())) {
            sampleResponse.setMessage("Modpas sa pa bon");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }
//        user.setToken(Helper.createToken(128));
//        userRepository.save(user);
        String token = TokenService.createAndStoreToken(user, enterprise.getId());

        sampleResponse.setMessage("Konekte avèk siksè");
//        sampleResponse.getBody().put("token",user.getToken());
        sampleResponse.getBody().put("token", token);
        sampleResponse.getBody().put("ok", true);
        sampleResponse.getBody().put("pos", pos);
        sampleResponse.getBody().put("seller", seller);
        sampleResponse.getBody().put("shifts", shiftRepository.findAllByEnterpriseIdOrderByIdDesc(enterprise.getId()));
        sampleResponse.getBody().put("CombinationTypes", combinationTypeRepository.findAllByEnterpriseIdOrderByIdDesc(enterprise.getId()));
        sampleResponse.getBody().put("combination", combinationRepository.findAllByEnabledAndEnterpriseId(false, enterprise.getId()));

        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout/enterprise/{id}", method = RequestMethod.POST, produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> logout(@RequestHeader("token") String token, @PathVariable("id") Long enterpriseId) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
        }

        if (!TokenService.contains(token)) {
            sampleResponse.setMessage("Sesyon an fèmen avèk siksè.");
            sampleResponse.getBody().put("ok", true);
            return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
        }
        TokenService.remove(token, enterpriseId);
        sampleResponse.setMessage("Sesyon an fèmen avèk siksè.");
        sampleResponse.getBody().put("ok", true);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/sale", method = RequestMethod.POST, produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> processSale(@RequestBody SaleViewModel vm, @RequestHeader("token") String token) {
        SampleResponse sampleResponse = new SampleResponse();
        sampleResponse.setMessages(new ArrayList<>());
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize kreye vant, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (!TokenService.contains(token)) {
            sampleResponse.setMessage("Ou pa otorize kreye vant, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Shift shift = shiftRepository.findShiftByEnabledAndEnterpriseId(true, vm.getShift().getEnterprise().getId());

        GlobalConfiguration globalConfiguration = globalConfigurationService.findGlobalConfiguration(vm.getEnterprise().getId());
        if (globalConfiguration == null){
            sampleResponse.setMessage("Gen yon pwoblèm nan konfigirasyon vant yo pou lè sa wap eseye fe vant lan, kontakte met bòlèt la pou plis enfòmasyon");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        if (globalConfiguration.isTransferSaleToAnotherShift()){
            if (shift != null && !shift.getId().equals(vm.getShift().getId())) {
                sampleResponse.getMessages().add("Tiraj " + vm.getShift().getName() + " pa aktive kounya, vant sa ap pase pou tiraj " + shift.getName());
            }
        } else {
            sampleResponse.setMessage("Ou pa otoriza fe vant nan lè sa paske bòlèt la femen, kontakte met bolèt la pou plis enfòmasyon");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < vm.getSaleDetails().size(); i++) {

            Combination combination = null;
            combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(vm.getSaleDetails().get(i).getCombination(), vm.getSaleDetails().get(i).getCombinationTypeId() ,vm.getEnterprise().getId());
            if(combination != null && !combination.isEnabled()) {
                sampleResponse.getMessages().add("Konbinezon " + vm.getSaleDetails().get(i).getCombination() + " an bloke retire li nan vant lan pou ou ka kontinye.");
                sampleResponse.getBody().put("ok", false);
                return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
            }
            if (vm.getSaleDetails().get(i).getProduct().equals(CombinationTypes.MARYAJ.name())) {
                String[] arr = vm.getSaleDetails().get(i).getCombination().split("x");
                combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(arr[0].trim() + "x" + arr[1].trim(), vm.getSaleDetails().get(i).getCombinationTypeId(), vm.getEnterprise().getId());
                if (combination == null) {
                    combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(arr[1].trim() + "x" + arr[0].trim(), vm.getSaleDetails().get(i).getCombinationTypeId(), vm.getEnterprise().getId());
                    vm.getSaleDetails().get(i).setCombination(arr[1].trim() + "x" + arr[0].trim());
                }
            } else {
                combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(vm.getSaleDetails().get(i).getCombination(), vm.getSaleDetails().get(i).getCombinationTypeId(), vm.getEnterprise().getId());
            }
            if ((combination.getSaleTotal() + vm.getSaleDetails().get(i).getPrice()) >= combination.getMaxPrice()) {
                LastNotification last = new LastNotification();
                last.setChanged(true);
                last.setEnterpriseId(vm.getEnterprise().getId());
                last.setDate(new Date());
                last.setEnterpriseId(vm.getEnterprise().getId());
                last.setIdType(combination.getId());
                last.setType(NotificationType.CombinationPriceLimit.ordinal());

                sampleResponse.getBody().put("combination", combination.getResultCombination());
                sampleResponse.getBody().put("type", combination.getCombinationType().getProducts().getName());
                sampleResponse.getBody().put("date", new Date());
                sampleResponse.getBody().put("shiftId", vm.getShift());

                Notification notification = new Notification();
                notification.setCreationDate(new Date());
                notification.setMessage("Konbinezon " + vm.getSaleDetails().get(i).getCombination() + " an rive nan limit pri nou ka bay li.");
                notification.setRead(false);
                notification.setType(NotificationType.CombinationPriceLimit.ordinal());
                notification.setLifetime(Long.valueOf(1440 * 60 * 1000));
                List<Users> users = userRepository.selectUserSuperAdminByEnterprise(Roles.ROLE_SUPER_ADMIN.name(), vm.getEnterprise().getId());
                if (users.size() > 0){
                    notification.setUsers(users.get(0));
                    notification.setEnterprise(enterpriseRepository.getOne(vm.getEnterprise().getId()));

                    notificationRepository.save(notification);
                    sampleResponse.getBody().put("notifications", notificationRepository.findAllByEnterpriseIdAndUsersIdAndReadFalse(vm.getEnterprise().getId(), users.get(0).getId()));
                }

                last.setSampleResponse(sampleResponse);
                auditService.sendMessage(sampleResponse, vm.getEnterprise().getId(), last);
                sampleResponse.getMessages().add("Konbinezon " + vm.getSaleDetails().get(i).getCombination() + " an rive nan limit pri nou ka bay li retirel pou ou ka kontinye vant lan.");
                sampleResponse.getBody().put("ok", false);
                return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
            }
        }

        Sale sale = apiService.mapSale(vm, shift);
        Sale savedSale = saleRepository.save(sale);
        savedSale.setEnabled(true);
        saleRepository.save(sale);

        sampleResponse.getBody().put("ticket", savedSale.getTicket());

        for (SaleDetail saleDetail : savedSale.getSaleDetails()) {
            Combination combination = combinationRepository.findCombinationById(saleDetail.getCombination().getId());
            if (combination != null) {
                combination.setSaleTotal(combination.getSaleTotal() + saleDetail.getPrice());
                combinationRepository.save(combination);
            }
        }

//        TODO: save the seller percentage on every sale
//        Seller seller = sellerRepository.findSellerById(vm.getSeller().getId());
//
//        if (seller.getPaymentType() == PaymentType.PERCENTAGE.ordinal()){
//            double amountBySale = (sale.getTotalAmount()* seller.getPercentageCharged())/100;
//            seller.setMonthlyPercentagePayment();
//        }
        sampleResponse.getBody().put("ok", true);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/complete/sale/enterprise/{enterpriseId}/ticket/{id}", produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> completeSale(@RequestHeader("token") String token, @PathVariable("enterpriseId") Long enterpriseId, @PathVariable("id") Long ticketId) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (!TokenService.contains(token)) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Ticket ticket = ticketRepository.findTicketByIdAndEnterpriseId(ticketId, enterpriseId);
        ticket.setCompleted(true);
        ticketRepository.save(ticket);

        sampleResponse.getBody().put("ok", true);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    // pay ticket


    // delete wrong ticket after 5 minutes
    @PostMapping(value = "/ticket/delete/", produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> deleteTicket(@RequestHeader("token") String token,
                                               @RequestBody TicketWonViewModel vm) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (!TokenService.contains(token)) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Ticket ticket = ticketRepository.findTicketByShortSerialAndEnterpriseId(vm.getSerial(), vm.getEnterprise().getId());

        if (ticket == null) {
            sampleResponse.setMessage("Ticket sa pa egziste");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        Sale sale = saleRepository.findSaleByTicketIdAndEnterpriseIdAndSellerId(ticket.getId(), vm.getEnterprise().getId(), vm.getSeller().getId());
        if (sale == null) {
            sampleResponse.setMessage("Ou pa otorize elimine tikè sa anko");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        GlobalConfiguration globalConfiguration = globalConfigurationService.findGlobalConfiguration(vm.getEnterprise().getId());
        if (globalConfiguration == null){
            sampleResponse.setMessage("Konfigirasyon sa poko egziste, kontakte met bolèt la pou plis enfòmasyon");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }
        if (!globalConfiguration.isCanDeleteTicket()){
            sampleResponse.setMessage("Dezole nou pa otorize elimine okenn tikè");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        long diff = Math.abs(new Date().getTime() - sale.getDate().getTime());

        long diffMinutes = diff / (60 * 1000) % 60;

        if (diffMinutes >= globalConfiguration.getTicketLifeTime()) {
            sampleResponse.setMessage(String.format("Ou pa ka elimine tikè sa ankò, paske ou kite {0} minit pase", globalConfiguration.getTicketLifeTime()));
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        for (SaleDetail saleDetail: sale.getSaleDetails()) {
            Combination combination = combinationRepository.findCombinationById(saleDetail.getCombination().getId());
            combination.setSaleTotal(combination.getSaleTotal() - saleDetail.getPrice());
            combinationRepository.save(combination);
            SaleDetail savedSaleDetail = saleDetailRepository.findByEnterpriseIdAndId(vm.getEnterprise().getId(), saleDetail.getId());
            saleDetail.setDeleted(true);
            saleDetailRepository.save(savedSaleDetail);
        }
        // delete ticket
        ticket.setDeleted(true);
        ticket.setShift(null);
        ticket.setEnterprise(null);
        ticketRepository.save(ticket);

        // delete sale
        sale.setPos(null);
        sale.setSeller(null);
        sale.setShift(null);
        sale.setEnterprise(null);
        sale.setTicket(null);
        sale.setDeleted(true);
        sale.setSaleDetails(null);
        saleRepository.save(sale);
//        saleRepository.deleteById(sale.getId());

        sampleResponse.getBody().put("ok", true);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }


    @GetMapping(value = "/ticket/replay/{enterprise}/{serial}", produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> replayTicket(@RequestHeader("token") String token,
                                               @PathVariable("serial") String serial,
                                               @PathVariable("enterprise") Long enterpriseId) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (!TokenService.contains(token)) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        GlobalConfiguration globalConfiguration = globalConfigurationService.findGlobalConfiguration(enterpriseId);
        if (globalConfiguration == null){
            sampleResponse.setMessage("Konfigirasyon sa poko egziste, kontakte met bolèt la pou plis enfòmasyon");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        if (!globalConfiguration.isCanReplayTicket()){
            sampleResponse.setMessage("Dezole nou pa otorize pou rejwe okenn tikè");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        Sale sale = saleRepository.findSaleByTicketShortSerialAndEnterpriseId(serial, enterpriseId);
        sampleResponse.getBody().put("sale", sale);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/ticket/won", produces = ACCEPT_TYPE, consumes = ACCEPT_TYPE)
    public ResponseEntity<Object> findWonTicket(@RequestHeader("token") String token,
                                                @RequestBody TicketWonViewModel vm) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (!TokenService.contains(token)) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            sampleResponse.getBody().put("ok", false);
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (vm.getShift().getId() > 0) {
            Shift shift = shiftRepository.findShiftByIdAndEnterpriseId(vm.getShift().getId(), vm.getEnterprise().getId());
            if (shift.getName().equals(Shifts.Maten.name())) {
                Shift newyork = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), vm.getEnterprise().getId());
                Pair<Date, Date> startAndEndDate = null;
                if (newyork != null) {
                    startAndEndDate = Helper.getStartDateAndEndDate(newyork.getCloseTime(), shift.getCloseTime(),
                            vm.getEmissionDate(), -1, "dd/MM/yyyy, hh:mm:ss a");
                }
                if (startAndEndDate != null) {
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), vm.getShift().getId(), startAndEndDate.getValue0(), startAndEndDate.getValue1()));
                }

                Draw draw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(vm.getEmissionDate(), "00:00:00".split(":")), vm.getEnterprise().getId(), shift.getId());
                if (draw != null) {
                    sampleResponse.getBody().put("draw", draw);
                }
                return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
            } else {
                Shift maten = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), vm.getEnterprise().getId());
                Pair<Date, Date> startAndEndDate = null;
                if (maten != null) {
                    startAndEndDate = Helper.getStartDateAndEndDate(maten.getCloseTime(), shift.getCloseTime(),
                            vm.getEmissionDate(), 0, "dd/MM/yyyy, hh:mm:ss aa");
                }
                if (startAndEndDate != null) {
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), vm.getShift().getId(), startAndEndDate.getValue0(), startAndEndDate.getValue1()));
                }
                Draw draw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(vm.getEmissionDate(), "00:00:00".split(":")), vm.getEnterprise().getId(), shift.getId());
                if (draw != null) {
                    sampleResponse.getBody().put("draw", draw);
                    return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
                }

            }
        }
        Shift activeShift = shiftRepository.findShiftByEnabledAndEnterpriseId(true, vm.getEnterprise().getId());
        Shift inactiveShift = shiftRepository.findShiftByEnabledAndEnterpriseId(false, vm.getEnterprise().getId());
        if (activeShift != null && inactiveShift != null) {
            if (activeShift.getName().equals(Shifts.Maten.name())) {
                Date date = Helper.addDays(new Date(), -1);
                Draw draw = globalHelper.getLastDraw(vm.getEnterprise().getId(), activeShift, inactiveShift);
                if (draw != null) {
                    sampleResponse.getBody().put("draw", draw);
                }
                Pair<Date, Date> startAndEndDate = Helper.getStartDateAndEndDate(activeShift.getCloseTime(), inactiveShift.getCloseTime(),
                        date, 0, "dd/MM/yyyy, hh:mm:ss aa");
                if (startAndEndDate != null) {
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), inactiveShift.getId(), startAndEndDate.getValue0(), startAndEndDate.getValue1()));
                }
            } else {
                Draw draw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(new Date(), "00:00:00".split(":")), vm.getEnterprise().getId(), inactiveShift.getId());
                if (draw != null) {
                    sampleResponse.getBody().put("draw", draw);
                }
                Pair<Date, Date> startAndEndDate = Helper.getStartDateAndEndDate(activeShift.getCloseTime(), inactiveShift.getCloseTime(),
                        new Date(), 0, "dd/MM/yyyy, hh:mm:ss aa");
                if (startAndEndDate != null) {
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), inactiveShift.getId(), startAndEndDate.getValue0(), startAndEndDate.getValue1()));
                }
            }
        }

//        sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdOrderByShiftIdDesc(vm.getEnterprise().getId(),vm.getSeller().getId()));
        sampleResponse.getBody().put("ok", true);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    //  Amount earned by seller

}
