package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Interaces.CombinationViewModel;
import com.b.r.loteriab.r.Model.Interaces.IReportViewModel;
import com.b.r.loteriab.r.Model.ViewModel.CombinationVm;
import com.b.r.loteriab.r.Model.ViewModel.SalesReportViewModel;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Repository.CombinationRepository;
import com.b.r.loteriab.r.Services.*;
import com.b.r.loteriab.r.Validation.Result;
import org.javatuples.Pair;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Dany on 12/05/2019.
 */

@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_SUPER_MEGA_ADMIN"})
public class GlobalRestController {
    @Autowired
    private DrawService drawService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CombinationTypeService combinationTypeService;

    @Autowired
    private PosService posService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CombinationService combinationService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private CombinationRepository combinationRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BankService bankService;

    @Autowired
    private AuditEventService service;

    @Autowired
    private ReportService reportService;

    private static final String ACCECPT_TYPE= "application/json";

    /**
     * Get All draw filtered
     * @param page
     * @param items
     * @param state
     * @param day
     * @param month
     * @param year
     * @return draws
     */
    @GetMapping(value = "/draw/find/{page}/item/{items}/state/{state}/day/{day}/month/{month}/year/{year}", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Draw>> findDrawByPage(@PathVariable("items")int items,
                                     @PathVariable("page")int page,
                                     @PathVariable("day")int day,
                                     @PathVariable("month")int month,
                                     @PathVariable("year")int year,
                                     @PathVariable("state")int state,
                                     HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Draw> draws = drawService.findAllDraw(page, items, getState(state), day, month, year, enterprise.getId());

            if (draws == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.OK); // TOD
    }

    /**
     * Get All Draws size
     * @param state
     * @return size
     */
    @GetMapping(value = "/draw/find/size/state/{state}", produces = ACCECPT_TYPE)
    public int getAllDraws(@PathVariable("state")int state,HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            return drawService.findAllDrawByEnabled(getState(state), enterprise.getId()).size();
        }
        return 0;
    }

    /**
     * Get All Draws
     * @return draws
     */

    @GetMapping(value = "/draw/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Draw>> getDrawList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Draw> draws = drawService.findAllDraw(enterprise.getId());
            if (draws.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(draws, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    /**
     * Get All Groups
     * @return Groups
     */

    @GetMapping(value = "/group/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Groups>> getGroupList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Groups> groups = groupsService.findAllGroups(enterprise.getId());
            if (groups.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All groups filtered
     * @param page
     * @param items
     * @param state
     * @return groups
     */
    @GetMapping(value = "/group/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Groups>> findGroupByPage(@PathVariable("items")int items,
                                         @PathVariable("page")int page,
                                         @PathVariable("state")int state,
                                        HttpServletRequest request
        ){ Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Groups> groups = groupsService.findAllGroupByState(page, items, getState(state), enterprise.getId());
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Combination type
     * @return combinationTypes
     */

    @GetMapping(value = "/combinationType/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<CombinationType>> getCombinationTypeList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<CombinationType> combinationTypes = combinationTypeService.findallByEnterpriseId(enterprise.getId());
            if (combinationTypes.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(combinationTypes, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Combination Type filtered
     * @param page
     * @param items
     * @param state
     * @return combinationTypes
     */
    @GetMapping(value = "/combinationType/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<CombinationType>> getCombinationTypeListFiltered(
        @PathVariable("items")int items,
        @PathVariable("page")int page,
        @PathVariable("state")int state,
        HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<CombinationType> combinationTypes = combinationTypeService.findAllCombinationTypeByEnterpriseId(page, items, getState(state), enterprise.getId());

            if (combinationTypes.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(combinationTypes, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code

    }

    /**
     * Get All Combination Type size
     * @param state
     * @return size
     */
    @GetMapping(value = "/combinationType/find/size/state/{state}", produces = ACCECPT_TYPE)
    public int getAllCombinationType(@PathVariable("state")int state, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null){
            return combinationTypeService.findAllByEnabledAndEnterpriseId(getState(state), enterprise.getId()).size();
        }
        return 0;
    }

    /**
     * Get All seller filtered
     * @param page
     * @param items
     * @param state
     * @return sellers
     */
    @GetMapping(value = "/seller/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Seller>> findSellerByPage(@PathVariable("items")int items,
                                         @PathVariable("page")int page,
                                         @PathVariable("state")int state,
                                         HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Seller> sellers =  sellerService.findAllSellerByState(page, items, getState(state), enterprise.getId());
            return new ResponseEntity<>(sellers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK); // //TODO: Sattus code
    }

    /**
     * Get All combination filtered
     * @return sellers
     */
    @GetMapping(value = "/combination/find", produces = ACCECPT_TYPE)
    public ResponseEntity<ArrayList<Combination>> findCombinationByPage(
                                                   HttpServletRequest request,
                                                   @RequestParam(defaultValue = "") String combination){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (combination.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            if (combination.matches("([^0-9xX\\s]+)"))
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            ArrayList<Combination> combinations = (ArrayList<Combination>) combinationService.findAllCombinations(combination, enterprise);
            if (combinations.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(combinations, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        //TODO: Sattus code
    }

    /**
     * Get All Bank
     * @return banks
     */
    @GetMapping(value = "/bank", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Bank>> getBankList(HttpServletRequest request,
                                                  @RequestParam(value ="count", defaultValue = "10") String count,
                                                  @RequestParam(value ="state", defaultValue = "1") String state,
                                                  @RequestParam(value ="page", defaultValue = "1") String page){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Bank> banks = bankService.findAllBankByState(Integer.parseInt(page), Integer.parseInt(count), getStateEj(state), enterprise.getId());
            if (banks.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK); //TODO: Sattus code
    }

    /**
     * Get All bank filtered
     * @param page
     * @param items
     * @param state
     * @return groups
     */
    @GetMapping(value = "/bank/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Bank>> findBankByPage(@PathVariable("items")int items,
                                                        @PathVariable("page")int page,
                                                        @PathVariable("state")int state,
                                                        HttpServletRequest request
    ){ Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Bank> banks = bankService.findAllBankByState(page, items, getState(state), enterprise.getId());
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Shift
     * @return shifts
     */
    @GetMapping(value = "/shift/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Shift>> getShiftList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Shift> shifts = shiftService.findAllByEnterpriseId(enterprise.getId());
            if (shifts.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(shifts, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK); //TODO: Sattus code
    }
    /**
     * Get All Seller
     * @return sellers
     */
    @GetMapping(value = "/seller/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Seller>> getSellerList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Seller> sellers = sellerService.findAllSellersByEnterpriseId(enterprise.getId());// TODO
            if (sellers.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(sellers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All users
     * * @param type
     * @return users
     */
    @GetMapping(value = "/user/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Users>> getUsersList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Users> users = usersService.findAllUsersExceptSuperAdminAndEnterpriseId(enterprise.getId());
            if (users.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);//TODO: Sattus code
    }

    @GetMapping(value = "/user/exist", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> getUsersList(HttpServletRequest request, @RequestParam(defaultValue = "") String username){
        HashMap map = new HashMap();
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (username.isEmpty())
            {
                map.put("exist", false);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            Users user = usersService.findUserByUsername(username);
            if (user!= null){
                map.put("exist", true);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
                map.put("exist", false);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All users
     * * @param type
     * @return users
     */
    @GetMapping(value = "/user/superAdmin", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Users>> getUsersSuperAdminList(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Users> users = usersService.findAllUsersSuperAdmin();
            if (users.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All User Filtered
     * @param state
     * @param page
     * @param items
     * @return users
     */
    @GetMapping(value = "/user/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Users>> UserListFiltered(
            @PathVariable("items")int items,
            @PathVariable("page")int page,
            @PathVariable("state")int state,
            HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Users> users = usersService.findAllUsersByState(page, items, getState(state), enterprise.getId());

            if (users == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All User Filtered SuperAdmin
     * @param state
     * @param page
     * @param items
     * @return users
     */
    @GetMapping(value = "/user/find/{page}/item/{items}/state/{state}/superAdmin", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Users>> UserListFilteredSuperAdmin(
            @PathVariable("items")int items,
            @PathVariable("page")int page,
            @PathVariable("state")int state,
            HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Users> users = usersService.findAllUsersByStateSuperAdmin(page, items, getState(state));
            if (users == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All POS
     * @return posList
     */

    @GetMapping(value = "/pos", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Pos>> getPosList(
            HttpServletRequest request,
            @RequestParam(value ="count", defaultValue = "10") String count,
            @RequestParam(value ="page", defaultValue = "1") String page,
            @RequestParam(value ="filter[id]", defaultValue = "") String id,
            @RequestParam(value ="filter[deskripsyon]", defaultValue = "") String description,
            @RequestParam(value ="filter[serial]", defaultValue = "") String serial,
            @RequestParam(value ="filter[datKreyasyon]", defaultValue = "") String creationDate,
            @RequestParam(value ="filter[actif]", defaultValue = "1") String state,
            @RequestParam(value ="sorting[id]", defaultValue = "desc") String sortId,
            @RequestParam(value ="sorting[deskripsyon]", defaultValue = "") String sortDescription,
            @RequestParam(value ="sorting[datKreyasyon]", defaultValue = "") String sortCreationDate,
            @RequestParam(value ="sorting[serial]", defaultValue = "") String sortSerial,
            @RequestParam(value ="sorting[actif]", defaultValue = "") String sortState){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Pos> pos = posService.findAllPosByState(Integer.parseInt(page),Integer.parseInt(count), getStateEj(state), enterprise.getId());
            if (pos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(pos, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);//TODO: Sattus code
    }

    /**
     * Get All Pos size
     * @param state
     * @return size
     */
    @GetMapping(value = "/pos/find/size/state/{state}", produces = ACCECPT_TYPE)
    public int getAllPosSize(@PathVariable("state")int state, HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            return posService.findPosByEnabled(getState(state), enterprise.getId()).size();
        }
        return 0;
    }

    /**
     * Get All Pos By seller
     * @param sellerId
     * @param updating
     * @return size
     */
    @GetMapping(value = "/pos/find/seller/{id}/{updating}", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Pos>> getAllPosBySeller(@PathVariable("id")Long sellerId,@PathVariable("updating")int updating,  HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Pos> pos = posService.findPosBySellerId(sellerId, enterprise.getId(),updating);
            if (pos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(pos, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Products
     * @return products
     */
    @GetMapping(value = "/combinationType/{enterpriseId}/find/products", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Products>> getAllProducts(@PathVariable("enterpriseId")Long enterpriseId, HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
           List<Products> products = productsService.findAll();
            if (products == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

    }

    /**
     * Get All Pos
     * @param state
     * @param items
     * @param page
     * @return size
     */
    @GetMapping(value = "/pos/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Pos>> getPosListFiltered(
            @PathVariable("items")int items,
            @PathVariable("page")int page,
            @PathVariable("state")int state,
            HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Page<Pos> pos = posService.findAllPosByState(page, items, getState(state), enterprise.getId());

            if (pos == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(pos, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Enterprise
     * @return enterpriseList
     */

    @GetMapping(value = "/enterprises/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Enterprise>> getEnterpriseList(){
        List<Enterprise> enterprises = enterpriseService.findAllEnterprise();
        if (enterprises.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(enterprises, HttpStatus.OK);
    }

    /**
     * Get All Enterprise size
     * @param state
     * @return size
     */
    @GetMapping(value = "/enterprises/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Enterprise>> getEnterpriseListFiltered(
            @PathVariable("items")int items,
            @PathVariable("page")int page,
            @PathVariable("state")int state
    ){
        Page<Enterprise> enterprises = enterpriseService.findAllEnterpriseByState(page, items, getState(state));

        if (enterprises == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(enterprises, HttpStatus.OK);
    }

    /**
     * Get actual enterprise
     * @return
     */
    @GetMapping(value = "/enterprise/find", produces = ACCECPT_TYPE)
    public ResponseEntity<Long> getActualEnterprise(HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null)
            return new ResponseEntity<>(enterprise.getId(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update combination Combination
     * @return result
     */


    @PutMapping(value = "/combination/update", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> saveCombinationConfiguration(@RequestBody CombinationVm combinationVm, HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            HashMap map = new HashMap();
            Combination combination = combinationService.findCombinationById(combinationVm.getId());

            if (combination == null) {
                map.put("saved", false);
                map.put("message", "Konbinezon an pa egziste cheche yon lot");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

            if (combinationVm.isChangeMaxPrice()) {
                if (combinationVm.getMaxPrice() > 0) {
                    combination.setMaxPrice(combinationVm.getMaxPrice());
                }
            }

            if (combinationVm.isChangeState()) {
                combination.setEnabled(combinationVm.isEnabled());
            }

            Result result = combinationService.updateCombination(combination);
            if (!result.isValid()) {
                map.put("saved", false);
                map.put("message", result.getLista().get(0).getMessage());
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }

            map.put("saved", true);

            SampleResponse sampleResponse = new SampleResponse();
            if (combinationVm.isChangeState()) {
                LastNotification last = new LastNotification();
                last.setChanged(true);
                last.setDate(new Date());
                last.setEnterpriseId(enterprise.getId());
                last.setIdType(combination.getId());
                last.setType(NotificationType.CombinationBlocked.ordinal());

                sampleResponse.getBody().put("combination", combinationRepository.findAllByEnabledAndEnterpriseId(false, enterprise.getId()));
                last.setSampleResponse(sampleResponse);
                service.sendMessage(sampleResponse, enterprise.getId(), last);
            }
            map.put("message", "Konbinezon aktyalize");

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
            return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Update combination Configuration Group
     * @return result
//     */
    @PutMapping(value = "/combination/group/update", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> saveCombinationConfigurationGroup(@RequestBody CombinationVm combinationVm,HttpServletRequest request){
        HashMap map = new HashMap();
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            int result = combinationService.updateCombinationGroup(combinationVm.getCombinationTypeId(), combinationVm.isEnabled(), combinationVm.getMaxPrice(), enterprise);
            if (result <= 0) {
                map.put("saved", false);
                map.put("message", "Konbinezon yo pa modifye reeseye ankò");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

            map.put("saved", true);
            map.put("message", "Konbinezon yo aktyalize");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("saved", false);
        map.put("message", "Ou pa gen akse pou ou modifye konbinezon yo");
        return new ResponseEntity<>(map, HttpStatus.OK);//TODO: Sattus code
    }


    /**
     * Update configuration shift
     * @return result
     */
    @PutMapping(value = "/shift/update", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> saveShfitConfiguration(@RequestBody Shift shift, HttpServletRequest request){
        HashMap map = new HashMap();
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            Shift currentShift = shiftService.findShiftById(shift.getId(), enterprise.getId());

            if (currentShift == null) {
                map.put("saved", false);
                map.put("message", "Tip Tiraj sa pa egziste cheche yon lot");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

            currentShift.setEnabled(shift.isEnabled());
            currentShift.setOpenTime(shift.getOpenTime());
            currentShift.setCloseTime(shift.getCloseTime());
            if (shift.getOpenTime().isEmpty() || shift.getCloseTime().isEmpty()) {
                map.put("saved", false);
                map.put("message", "Ou dwe mete yon dat ouveti ak yon dat li femen");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

            Result result = shiftService.updateShift(currentShift, enterprise.getId());
            if (!result.isValid()) {
                map.put("saved", false);
                map.put("message", result.getLista().get(0).getMessage());
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }

            map.put("saved", true);
            map.put("message", "Tip tiraj aktyalize");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("saved", false);
        map.put("message", "Tip tiraj la pa ka aktyalize, ou pa gen pemisyon pou sa");
        return new ResponseEntity<>( null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get All Sales Report
     * @return size
     */
    @PutMapping(value = "/sales/report", produces = ACCECPT_TYPE)
    public ResponseEntity<List<IReportViewModel>> getSalesReport(
            @RequestBody SalesReportViewModel salesReportViewModel,
            HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            salesReportViewModel.setEnterpriseId(enterprise.getId());
          return new ResponseEntity<>(reportService.getSalesReport(salesReportViewModel), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Connected User
     * @return users
     */
    @GetMapping(value = "/user/connected", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Pair<Long, String>>> getConnectedUsers(
            HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Pair<Long, String>> users = TokenService.getConnectedUsers(enterprise.getId());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    /**
     * Get All Top 3 sold combination by product in an enterprise
     * @return users
     */
    @GetMapping(value = "/combination/top/sold", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Combination>> getTop3SoldCombinationByEnterprise(
            HttpServletRequest request
    ){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            List<Combination> combinations = combinationService.selectTop3MostSoldCombinationByCombintionType(enterprise.getId());
            return new ResponseEntity<>(combinations, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);//TODO: Sattus code
    }

    private Boolean getStateEj (String state){
        int resState = Integer.parseInt(state);
        Boolean resultState = false;
        if (resState == 0){
            resultState = false;
        }
        if (resState == 1){
            resultState = null;
        }
        if (resState == 2){
            resultState = true;
        }
        return resultState;
    }

    private Boolean getState (int state){
        Boolean resultState = false;
        if (state == 0){
            resultState = false;
        }
        if (state == 1){
            resultState = null;
        }
        if (state == 2){
            resultState = true;
        }
        return resultState;
    }
}
