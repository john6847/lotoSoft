package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Enums.Shifts;
import com.b.r.loteriab.r.Model.ViewModel.*;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Repository.*;
import com.b.r.loteriab.r.Services.ApiService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.GlobalHelper;
import com.b.r.loteriab.r.Validation.Helper;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */
@RestController
@RequestMapping("/deadRouteBR")
public class RestApiController {

    @Autowired
    private UsersService usersService;

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


    private static final String ACCECPT_TYPE= "application/json";

    @RequestMapping(value = "/login",  method = RequestMethod.POST, produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> authenticate(@RequestBody UserViewModel vm){
        SampleResponse sampleResponse = new SampleResponse();
        Enterprise enterprise = enterpriseRepository.findEnterpriseByName(vm.getEnterpriseName());
        Pos pos = posRepository.findBySerialAndEnabledAndEnterpriseId(vm.getSerial(), true, enterprise.getId());
//        TODO: obtener el serial del pos que no se hardcoded
        if (pos == null) {
            System.out.println("Pos null");
            sampleResponse.setMessage("Machin sa pa gen pèmisyon konekte");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        if (vm.getEnterpriseName()== null ){
            sampleResponse.setMessage("Ou sipoze voye non antrepriz la");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        Enterprise savedEnterprise = enterpriseRepository.findEnterpriseByEnabledAndNameContainingIgnoreCase(true, vm.getEnterpriseName());
        if (savedEnterprise == null ){
            sampleResponse.setMessage("Non antrepriz la pa bon reeseye avek yon lot non pou ou ka konekte");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        Users user = userRepository.findByUsernameAndEnterpriseId(vm.getUsername(),enterprise.getId());
        if (vm.getUsername().isEmpty() || vm.getPassword().isEmpty()) {
            sampleResponse.setMessage("Itilizatè oubyen modpas la pa bon");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        if (user == null){
            sampleResponse.setMessage("Itilizatè sa pa egziste");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }
        
        Seller seller = sellerRepository.findByUserIdAndEnterpriseId(user.getId(), enterprise.getId());
        if (seller == null) {
            sampleResponse.setMessage("Vandè sa pa egziste");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        } else {
            if(!seller.isEnabled()) {
                sampleResponse.setMessage("Vandè sa pa gen pèmisyon konekte");
                return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
            }
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(vm.getPassword(), user.getPassword())){
            sampleResponse.setMessage("Modpas sa pa bon");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }
        user.setToken(Helper.createToken(128));
        userRepository.save(user);
        sampleResponse.setMessage("Konekte avèk siksè");
        sampleResponse.getBody().put("token",user.getToken());
        sampleResponse.getBody().put("pos", pos);
        sampleResponse.getBody().put("seller", seller);
        sampleResponse.getBody().put("shifts", shiftRepository.findAllByEnterpriseId(enterprise.getId()));
        sampleResponse.getBody().put("CombinationTypes", combinationTypeRepository.findAllByEnterpriseId(enterprise.getId()));
        sampleResponse.getBody().put("combination", combinationRepository.findAllByEnabledAndEnterpriseId(false, enterprise.getId()));

        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout/enterprise/{id}",  method = RequestMethod.POST, produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> logout(@RequestHeader("token") String token, @PathVariable("id") Long enterpriseId){
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()){
            sampleResponse.setMessage("Itilizatè sa pat ouvri sesyon, li pa nesesè pou li fèmen sesyon an.");
            return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
        }
        Users users = userRepository.findUsersByTokenAndEnterpriseId(token, enterpriseId);

        if (users == null){
            sampleResponse.setMessage("Sesyon Itilizatè sa pa valid, li pa nesesè pou li fèmen sesyon an.");
            return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
        }
        users.setToken("");
        Users loggedOutUser = userRepository.save(users);
        if (loggedOutUser != null){
            if (loggedOutUser.getToken().isEmpty()){
                sampleResponse.setMessage("Sesyon an fèmen avèk siksè.");
                return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
            }else {
                sampleResponse.setMessage("Sesyon an pa rive fèmen, reeseye yon lòt fwa.");
                return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
            }
        }
        sampleResponse.setMessage("Sesyon an pa rive fèmen, reeseye yon lòt fwa.");
        return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/sale",  method = RequestMethod.POST, produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> processSale(@RequestBody SaleViewModel vm, @RequestHeader("token") String token){
        SampleResponse sampleResponse = new SampleResponse();
        sampleResponse.setMessages(new ArrayList<>());
        if (token.isEmpty()){
            sampleResponse.setMessage("Ou pa otorize kreye vant, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, vm.getEnterprise().getId()) == null){
            sampleResponse.setMessage("Ou pa otorize kreye vant, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Shift shift = shiftRepository.findShiftByEnabledAndEnterpriseId(true, vm.getShift().getEnterprise().getId());

        if (shift!=null && !shift.getId().equals(vm.getShift().getId())) {
            sampleResponse.getMessages().add("Tiraj "+ vm.getShift().getName()+ " pa aktive kounya, vant sa ap pase pou tiraj "+ shift.getName());
        }

        for (SaleDetailViewModel saleDetailViewModel: vm.getSaleDetails()){
            Combination combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(saleDetailViewModel.getCombination(), saleDetailViewModel.getCombinationTypeId(), vm.getEnterprise().getId());
            if ((combination.getSaleTotal() + saleDetailViewModel.getPrice()) >= combination.getMaxPrice()) {
                LastNotification last = new LastNotification();
                last.setChanged(true);
                last.setDate(new Date());
                last.setEnterpriseId(vm.getEnterprise().getId());
                last.setIdType(combination.getId());
                last.setType(NotificationType.CombinationPriceLimit.ordinal());

                sampleResponse.getBody().put("message", String.format("Konbinezon %s rive nan limit li ka vann pou tiraj sa", combination.getResultCombination()));
                last.setSampleResponse(sampleResponse);
                auditService.sendMessage(sampleResponse, vm.getEnterprise().getId(), last);
                sampleResponse.getMessages().add("Konbinezon "+ saleDetailViewModel.getCombination() + " an rive nan limit pri nou ka bay li retirel pou ou ka kontinye vant lan.");
//              TODO: Notificar el propietario  que esta combinacion ya ha llegado a su limite en la pagina principal
                return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
            }
        }

        Sale sale = apiService.mapSale(vm, shift);
        Sale savedSale = saleRepository.save(sale);
        savedSale.setEnabled(true);
        saleRepository.save(sale);
//        TODO: Saving Sale and return ticket to the seller
        sampleResponse.getBody().put("ticket", savedSale.getTicket());

//        TODO: save the seller percentage on every sale
//        Seller seller = sellerRepository.findSellerById(vm.getSeller().getId());
//
//        if (seller.getPaymentType() == PaymentType.PERCENTAGE.ordinal()){
//            double amountBySale = (sale.getTotalAmount()* seller.getPercentageCharged())/100;
//            seller.setMonthlyPercentagePayment();
//        }
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/complete/sale/enterprise/{enterpriseId}/ticket/{id}",  produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> completeSale(@RequestHeader("token") String token,@PathVariable("enterpriseId")Long enterpriseId, @PathVariable("id")Long ticketId){
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()){
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, enterpriseId) == null){
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
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
    @PostMapping(value = "/ticket/delete/", produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> deleteTicket (@RequestHeader("token") String token,
                                                @RequestBody TicketWonViewModel vm) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, vm.getEnterprise().getId()) == null) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Ticket ticket = ticketRepository.findTicketBySerialAndEnterpriseId(vm.getSerial(), vm.getEnterprise().getId());

        if (ticket == null){
            sampleResponse.setMessage("Ticket sa pa egziste");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        Sale sale = saleRepository.findSaleByTicketIdAndEnterpriseIdAndSellerId(ticket.getId(), vm.getEnterprise().getId(), vm.getSeller().getId());
        if (sale  == null){
            sampleResponse.setMessage("Ou pa otorize elimine Ticket sa anko");
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        Duration duration = Duration.between(LocalDateTime.now(), sale.getDate().toInstant());
        long diff = Math.abs(duration.toMinutes());

        if (diff >= 5){
            sampleResponse.setMessage("Ou pa ka elimine Ticket sa anko, paske ou kite 5 minit pase");
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        saleRepository.deleteSaleByTicketIdAndEnterpriseId(ticket.getId(), vm.getEnterprise().getId());

        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/ticket/replay/{enterprise}/{serial}",  produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> replayTicket (@RequestHeader("token") String token,
                                                @PathVariable("serial") String serial,
                                                @PathVariable("enterprise") Long enterpriseId) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, enterpriseId) == null) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Sale sale = saleRepository.findSaleByTicketSerialAndEnterpriseId(serial, enterpriseId);
        sampleResponse.getBody().put("sale", sale);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/ticket/won",  produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> findWonTicket (@RequestHeader("token") String token,
                                                @RequestBody TicketWonViewModel vm) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, vm.getEnterprise().getId()) == null) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (vm.getShift().getId() > 0 ){
            Shift shift = shiftRepository.findShiftByIdAndEnterpriseId( vm.getShift().getId(), vm.getEnterprise().getId());
            if (shift.getName().equals(Shifts.Maten.name())){
                Shift newyork = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.New_York.name(), vm.getEnterprise().getId());
                Pair<Date, Date> startAndEndDate = null;
                if (newyork != null){
                    startAndEndDate= Helper.getStartDateAndEndDate(newyork.getCloseTime(), shift.getCloseTime(),
                            vm.getEmissionDate(), -1, "dd/MM/yyyy, hh:mm:ss a");
                }
                if (startAndEndDate != null) {
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), vm.getShift().getId(), startAndEndDate.getValue0(),startAndEndDate.getValue1()));
                }

                Draw draw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(vm.getEmissionDate(), "00:00:00".split(":")),vm.getEnterprise().getId(), shift.getId());
                if(draw != null){
                    sampleResponse.getBody().put("draw", draw);
                }
                return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
            } else {
                Shift maten = shiftRepository.findShiftByNameAndEnterpriseId(Shifts.Maten.name(), vm.getEnterprise().getId());
                Pair<Date, Date> startAndEndDate = null;
                if (maten != null){
                    startAndEndDate = Helper.getStartDateAndEndDate(maten.getCloseTime(), shift.getCloseTime(),
                            vm.getEmissionDate(), 0, "dd/MM/yyyy, hh:mm:ss aa");
                }
                if (startAndEndDate != null){
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), vm.getShift().getId(), startAndEndDate.getValue0(),startAndEndDate.getValue1()));
                }
                Draw draw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(vm.getEmissionDate(), "00:00:00".split(":")),vm.getEnterprise().getId(), shift.getId());
                if(draw != null){
                    sampleResponse.getBody().put("draw", draw);
                    return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
                }

            }
        }
        Shift activeShift = shiftRepository.findShiftByEnabledAndEnterpriseId(true, vm.getEnterprise().getId());
        Shift inactiveShift = shiftRepository.findShiftByEnabledAndEnterpriseId(false, vm.getEnterprise().getId());
        if (activeShift != null && inactiveShift != null){
            if (activeShift.getName().equals(Shifts.Maten.name())){
                Date date = Helper.addDays(new Date(), -1);
                Draw draw = globalHelper.getLastDraw(vm.getEnterprise().getId(), activeShift, inactiveShift);
                if(draw != null){
                    sampleResponse.getBody().put("draw", draw);
                }
                Pair<Date, Date> startAndEndDate = Helper.getStartDateAndEndDate(activeShift.getCloseTime(), inactiveShift.getCloseTime(),
                            date, 0, "dd/MM/yyyy, hh:mm:ss aa");
                if (startAndEndDate != null){
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), inactiveShift.getId(), startAndEndDate.getValue0(),startAndEndDate.getValue1()));
                }
            } else {
                Draw draw = drawRepository.findByDrawDateAndEnterpriseIdAndShiftId(Helper.setTimeToDate(new Date(), "00:00:00".split(":")),vm.getEnterprise().getId(), inactiveShift.getId());
                if(draw != null){
                    sampleResponse.getBody().put("draw", draw);
                }
                Pair<Date, Date> startAndEndDate = Helper.getStartDateAndEndDate(activeShift.getCloseTime(), inactiveShift.getCloseTime(),
                        new Date(), 0, "dd/MM/yyyy, hh:mm:ss aa");
                if (startAndEndDate != null){
                    sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(
                            vm.getEnterprise().getId(), vm.getSeller().getId(), inactiveShift.getId(), startAndEndDate.getValue0(),startAndEndDate.getValue1()));
                }
            }
        }

//        sampleResponse.getBody().put("wonsales", saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdOrderByShiftIdDesc(vm.getEnterprise().getId(),vm.getSeller().getId()));
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }

    //  Amount earned by seller



}
