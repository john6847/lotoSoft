package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.Interaces.CombinationViewModel;
import com.b.r.loteriab.r.Model.ViewModel.CombinationVm;
import com.b.r.loteriab.r.Repository.CombinationRepository;
import com.b.r.loteriab.r.Services.*;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Dany on 12/05/2019.
 */

@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
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
    public List<Draw> findDrawByPage(@PathVariable("items")int items,
         @PathVariable("page")int page,
         @PathVariable("day")int day,
         @PathVariable("month")int month,
         @PathVariable("year")int year,
         @PathVariable("state")int state){

        List<Draw> draws = drawService.findAllDraw(page, items, getState(state), day, month, year);

        if (draws==null){
            return null;
        }
        return draws;
    }

    /**
     * Get All Draws size
     * @param state
     * @return size
     */
    @GetMapping(value = "/draw/find/size/state/{state}", produces = ACCECPT_TYPE)
    public int getAllDraws(@PathVariable("state")int state){
        return drawService.findAllDrawByEnabled(getState(state)).size();
    }

    /**
     * Get All Draws
     * @return draws
     */

    @GetMapping(value = "/draw/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Draw>> getDrawList(){
        List<Draw> draws = drawService.findAllDraw();
        if (draws.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(draws, HttpStatus.OK);
    }

    /**
     * Get All Groups
     * @return Groups
     */

    @GetMapping(value = "/group/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Groups>> getGroupList(){
        List<Groups> groups = groupsService.findAllGroups();
        if (groups.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    /**
     * Get All groups filtered
     * @param page
     * @param items
     * @param state
     * @return groups
     */
    @GetMapping(value = "/group/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public Page<Groups> findGroupByPage(@PathVariable("items")int items,
                                         @PathVariable("page")int page,
                                         @PathVariable("state")int state){
        return groupsService.findAllGroupByState(page, items, getState(state));
    }

    /**
     * Get All Combination type
     * @return combinationTypes
     */

    @GetMapping(value = "/combinationType/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<CombinationType>> getcombinationTypeList(){
        List<CombinationType> combinationTypes = combinationTypeService.findall();
        if (combinationTypes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(combinationTypes, HttpStatus.OK);
    }

    /**
     * Get All Combination Type filtered
     * @param page
     * @param items
     * @param state
     * @return combinationTypes
     */
    @GetMapping(value = "/combinationType/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<CombinationType>> getcombinationTypeListFiltered(
        @PathVariable("items")int items,
        @PathVariable("page")int page,
        @PathVariable("state")int state
    ){
        Page<CombinationType> combinationTypes = combinationTypeService.findAllCombinationType(page, items, getState(state));

        if (combinationTypes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(combinationTypes, HttpStatus.OK);
    }

    /**
     * Get All Combination Type size
     * @param state
     * @return size
     */
    @GetMapping(value = "/combinationType/find/size/state/{state}", produces = ACCECPT_TYPE)
    public int getAllCombinationType(@PathVariable("state")int state){
        return combinationTypeService.findAllByEnabled(getState(state)).size();
    }

    /**
     * Get All seller filtered
     * @param page
     * @param items
     * @param state
     * @return sellers
     */
    @GetMapping(value = "/seller/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public Page<Seller> findSellerByPage(@PathVariable("items")int items,
                                     @PathVariable("page")int page,
                                     @PathVariable("state")int state){
        return sellerService.findAllSellerByState(page, items, getState(state));
    }

    /**
     * Get All combination filtered
     * @param items
     * @return sellers
     */
    @GetMapping(value = "/combination/find/item/{items}", produces = ACCECPT_TYPE)
    public ResponseEntity<ArrayList<CombinationViewModel>> findCombinationByPage(
                                                   HttpServletRequest request,
                                                   @PathVariable("items")int items,
                                                   @RequestParam(defaultValue = "") String combination){
        if (combination.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (combination.matches("([^0-9xX\\s]+)"))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        ArrayList<CombinationViewModel> combinations =(ArrayList<CombinationViewModel>)combinationService.findAllCombinations(combination, items);
      if (combinations.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(combinations, HttpStatus.OK);
    }

    /**
     * Get All Shifts
     * @return shifts
     */
    @GetMapping(value = "/shift/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Shift>> getShiftList(){
        List<Shift> shifts = shiftService.findAll();
        if (shifts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }
    /**
     * Get All Seller
     * @return sellers
     */
    @GetMapping(value = "/seller/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Seller>> getSellerList(){
        List<Seller> sellers = sellerService.findAllSellers();
        if (sellers.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    /**
     * Get All users
     * * @param type
     * @return users
     */
    @GetMapping(value = "/user/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Users>> getUsersList(){
        List<Users> users = usersService.findAllUsersExceptSuperAdmin();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get All users
     * * @param type
     * @return users
     */
    @GetMapping(value = "/user/superAdmin", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Users>> getUsersSuperAdminList(){
        List<Users> users = usersService.findAllUsersSuperAdmin();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
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
            @PathVariable("state")int state
    ){

        Page<Users> users = usersService.findAllUsersByState(page, items, getState(state));

        if (users == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
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
            @PathVariable("state")int state
    ){

        Page<Users> users = usersService.findAllUsersByStateSuperAdmin(page, items, getState(state));

        if (users == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get All POS
     * @return posList
     */

    @GetMapping(value = "/pos/", produces = ACCECPT_TYPE)
    public ResponseEntity<List<Pos>> getPosList(){
        List<Pos> pos = posService.findAllPos();
        if (pos.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(pos, HttpStatus.OK);
    }

    /**
     * Get All Pos size
     * @param state
     * @return size
     */
    @GetMapping(value = "/pos/find/size/state/{state}", produces = ACCECPT_TYPE)
    public int getAllPosSize(@PathVariable("state")int state){
        return posService.findPosByEnabled(getState(state)).size();
    }

    /**
     * Get All Products
     * @return products
     */
    @GetMapping(value = "/combinationType/find/products", produces = ACCECPT_TYPE)
    public List<Products> getAllProducts(){
        return productsService.findAll();
    }

    /**
     * Get All Pos size
     * @param state
     * @return size
     */
    @GetMapping(value = "/pos/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
    public ResponseEntity<Page<Pos>> getPosListFiltered(
            @PathVariable("items")int items,
            @PathVariable("page")int page,
            @PathVariable("state")int state
    ){
        Page<Pos> pos = posService.findAllPosByState(page, items, getState(state));

        if (pos == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(pos, HttpStatus.OK);
    }

    /**
     * Get All Enterprise
     * @return enterpriseList
     */

    @GetMapping(value = "/enterprise/", produces = ACCECPT_TYPE)
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
    @GetMapping(value = "/enterprise/find/{page}/item/{items}/state/{state}", produces = ACCECPT_TYPE)
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

    @SendTo("/topic/posts/created")
    public String testCombination(){
        return "Hola";
    }

    /**
     * Update combination Combination
     * @return result
     */


    @PutMapping(value = "/combination/update", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> saveCombinationConfiguration(@RequestBody CombinationVm combinationVm){
        HashMap map = new HashMap();
        Combination combination = combinationService.findCombinationById(combinationVm.getId());

        if (combination == null){
            map.put("saved", false);
            map.put("message", "Konbinezon an pa egziste cheche yon lot");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }

        if (combinationVm.isChangeMaxPrice()){
            if (combinationVm.getMaxPrice()>0) {
                combination.setMaxPrice(combinationVm.getMaxPrice());
            }
        }

        if (combinationVm.isChangeState()){
            combination.setEnabled(combinationVm.isEnabled());
        }

        Result result = combinationService.updateCombination(combination);
        if(!result.isValid()){
            map.put("saved", false);
            map.put("message", result.getLista().get(0).getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        map.put("saved", true);
        map.put("message", "Konbinezon aktyalize");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Update combination Configuration Group
     * @return result
//     */
    @PutMapping(value = "/combination/group/update", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> saveCombinationConfigurationGroup(@RequestBody CombinationVm combinationVm){
        HashMap map = new HashMap();

        int result = combinationService.updateCombinationGroup(combinationVm.getCombinationTypeId(), combinationVm.isEnabled(), combinationVm.getMaxPrice());
        if (result <= 0){
            map.put("saved", false);
            map.put("message", "Konbinezon yo pa modifye reeseye ankò");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }

        map.put("saved", true);
        map.put("message", "Konbinezon yo aktyalize");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /**
     * Update configuration shift
     * @return result
     */
    @PutMapping(value = "/shift/update", produces = ACCECPT_TYPE)
    public ResponseEntity<Map> saveShfitConfiguration(@RequestBody Shift shift){
        HashMap map = new HashMap();
        Shift currentShift = shiftService.findShiftId(shift.getId());

        if (currentShift == null){
            map.put("saved", false);
            map.put("message", "Tip Tiraj sa pa egziste cheche yon lot");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }

        currentShift.setEnabled(shift.isEnabled());
        currentShift.setOpenTime(shift.getOpenTime());
        currentShift.setCloseTime(shift.getCloseTime());
        this.testCombination();
        if (shift.getOpenTime().isEmpty() || shift.getCloseTime().isEmpty()) {
            map.put("saved", false);
            map.put("message", "Ou dwe mete yon dat ouveti ak yon dat li femen");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }

        Result result = shiftService.updateShift(currentShift);
        if(!result.isValid()){
            map.put("saved", false);
            map.put("message", result.getLista().get(0).getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        map.put("saved", true);
        map.put("message", "Tip tiraj aktyalize");
        return new ResponseEntity<>(map, HttpStatus.OK);
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
