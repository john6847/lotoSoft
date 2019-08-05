package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.ViewModel.*;
import com.b.r.loteriab.r.Repository.*;
import com.b.r.loteriab.r.Services.ApiService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketHandler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private static final String ACCECPT_TYPE= "application/json";

    @GetMapping(value = "/user", produces = ACCECPT_TYPE)
    public ResponseEntity<String[]> getUsers(){
        String [] users = {"Dany", "Widzer", "Ketya"};
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/login",  method = RequestMethod.POST, produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> authenticate(@RequestBody UserViewModel vm){
        SampleResponse sampleResponse = new SampleResponse();
        System.out.println(vm.toString());
        Enterprise enterprise = enterpriseRepository.findEnterpriseByName(vm.getEnterpriseName());
        Pos pos = posRepository.findBySerialAndEnabledAndEnterpriseId(vm.getSerial(), true, enterprise.getId());
        if (pos == null) {
            System.out.println("Pos null");
            sampleResponse.setMessage("Machin sa pa gen pèmisyon konekte");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

//        if (pos.getEnterprise() == null ){
//            sampleResponse.setMessage("Machin sa pa gen pèmisyon konekte");
//            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
//        }

//        if (pos.getEnterprise()!= null && !pos.getEnterprise().isEnabled()){
//            // Opsyonal: Ajoute nimewo met bolet la
//            sampleResponse.setMessage("Machin sa pa gen pèmisyon konekte,kontakte met bolèt la pou plis enfo");
//            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
//        }

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
        if (token.isEmpty()){
            sampleResponse.setMessage("Ou pa otorize kreye vant, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, vm.getEnterprise().getId()) == null){
            sampleResponse.setMessage("Ou pa otorize kreye vant, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Shift shift = shiftRepository.findShiftByEnabledAndEnterpriseId(true, vm.getShift().getEnterprise().getId());
        if (!shift.getId().equals(vm.getShift().getId())) {
            sampleResponse.getMessages().add("Tiraj "+ vm.getShift().getName()+ " pa aktive kounya, vant sa ap pase pou tiraj "+ shift.getName());
        }

        boolean haveMax = false;
        for (SaleDetailViewModel saleDetailViewModel: vm.getSaleDetails()){
            Combination combination = combinationRepository.findByResultCombinationAndCombinationTypeIdAndEnterpriseId(saleDetailViewModel.getCombination(), saleDetailViewModel.getCombinationTypeId(), vm.getEnterprise().getId());
            if ((combination.getSaleTotal() + saleDetailViewModel.getPrice()) >= combination.getMaxPrice()) {
                haveMax = true;
                sampleResponse.getMessages().add("Konbinezon "+ saleDetailViewModel.getCombination() + " depase pri maksimòm ou ka vann li an retirel pou ka kontinye.");
            }
        }
        if (haveMax){
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
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


    @GetMapping(value = "/ticket/won/enterprise/{enterpriseId}/seller/{sellerId}", produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> getWonTicket(@RequestHeader("token") String token,@PathVariable("enterpriseId")Long enterpriseId, @PathVariable("sellerId")Long sellerId) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, enterpriseId) == null) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);
        Date dateBefore7Days = cal.getTime();

        List<Sale> winningSales =  saleRepository.findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndDateAfter(enterpriseId, sellerId, dateBefore7Days);

        sampleResponse.getBody().put("wonSales", winningSales);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }


    // delete wrong ticket after 5 minutes
    @RequestMapping(value = "/ticket/delete/enterprise/{enterpriseId}/{ticketId}",  method = RequestMethod.POST, produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
    public ResponseEntity<Object> deleteTicket (@RequestHeader("token") String token,
                                                @PathVariable("enterpriseId")Long enterpriseId,
                                                @PathVariable("ticketId") Long ticketId) {
        SampleResponse sampleResponse = new SampleResponse();
        if (token.isEmpty()) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findUsersByTokenAndEnterpriseId(token, enterpriseId) == null) {
            sampleResponse.setMessage("Ou pa otorize reyalize operasyon sa, reouvri sesyon an pou ou ka kontinye vann");
            return new ResponseEntity<>(sampleResponse, HttpStatus.UNAUTHORIZED);
        }

        Ticket ticket = ticketRepository.findTicketByIdAndEnterpriseId(ticketId, enterpriseId);

        if (ticket == null){
            sampleResponse.setMessage("Ticket sa pa egziste");
            return new ResponseEntity<>(sampleResponse, HttpStatus.NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesBehind = now.minusMinutes(5);

        Duration duration = Duration.between(now, fiveMinutesBehind);
        long diff = Math.abs(duration.toMinutes());

        if (diff >= 5){
            sampleResponse.setMessage("Ou pa ka elimine Ticket sa anko, paske ou kite 5 minit pase");
            return new ResponseEntity<>(sampleResponse, HttpStatus.BAD_REQUEST);
        }

        saleRepository.deleteSaleByTicketIdAndEnterpriseId(ticketId, enterpriseId);

        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/ticket/won",  method = RequestMethod.POST, produces = ACCECPT_TYPE, consumes = ACCECPT_TYPE)
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

        List<Ticket> tickets = ticketRepository.findAllByWonTrueAndEnterpriseIdAndEmissionDateAndShiftId(vm.getEnterprise().getId(), vm.getEmissionDate(), vm.getShift().getId());

        sampleResponse.getBody().put("tickets", tickets);
        return new ResponseEntity<>(sampleResponse, HttpStatus.OK);
    }
    //  Amount earned by seller
}
