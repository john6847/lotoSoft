package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Repository.*;
import com.b.r.loteriab.r.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Dany on 09/05/2019.
 */
@Controller
@ControllerAdvice
@RequestMapping("/configuration")
public class ConfigurationController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private CombinationTypeService combinationTypeService;

    @Autowired
    private CombinationTypeRepository combinationTypeRepository;

    @Autowired
    private PosService posService;

    @Autowired
    private PosRepository posRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private DrawService drawService;

    @Autowired
    private DrawRepository drawRepository;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    /**
     * Route to configigure Combination Type (Block and UnBlock)
     * @param id
     * @return configuration
     */

    @GetMapping("/combinationType/{id}")
    public String configurationCombinationType (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo konbinezon sa pa egziste, itilize yon lòt");
            return "redirect:/combinationType";
        }
        CombinationType  combinationType = combinationTypeService.findCombinationTypeById(id);
        if(combinationType==null){
            model.addAttribute("error","Konbinezon sa pa egziste.");
        }else {
            combinationType.setEnabled(!combinationType.isEnabled());
            combinationTypeRepository.save(combinationType);
        }

        return "redirect:/combinationType";
    }

    /**
     * Route to configigure Pos Type (Block and UnBlock)
     * @param id
     * @return configuration
     */

    @GetMapping("/pos/{id}")
    public String configurationPos (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo machin sa pa egziste, itilize yon lòt");
            return "redirect:/pos";
        }
        Pos pos= posService.findPosById(id);
        if(pos == null){
            model.addAttribute("error","Machin sa pa egziste.");
        }else {
            pos.setEnabled(!pos.isEnabled());
            posRepository.save(pos);
        }

        return "redirect:/pos";
    }

    /**
     * Route to configure user (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public String configurationUser (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo itilizatè sa pa egziste, itilize yon lòt");
            return "redirect:/user";
        }
        Users users = usersService.findUser(id);
        if(users==null){
            model.addAttribute("error","Itilizatè sa pa egziste");
        }else {
            users.setEnabled(!users.isEnabled());
            userRepository.save(users);
        }

        return "redirect:/user";
    }

    /**
     * Route to configure draw (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/draw/{id}")
    public String configurationDraw (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo Tiraj sa pa egziste, itilize yon lòt");
            return "redirect:/draw";
        }
        Draw draw = drawService.findDrawById(id);
        if(draw==null){
            model.addAttribute("error","Tiraj sa pa egziste");
        }else {
            draw.setEnabled(!draw.isEnabled());
            drawRepository.save(draw);
        }

        return "redirect:/draw";
    }

    /**
     * Route to configure group (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/group/{id}")
    public String configurationGroup (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo Gwoup sa pa egziste, itilize yon lòt");
            return "redirect:/group";
        }
        Groups groups = groupsService.findGroupsById(id);
        if(groups==null){
            model.addAttribute("error","Gwoup sa pa egziste");
        }else {
            groups.setEnabled(!groups.isEnabled());
            List<Seller> sellers = sellerService.findAllSellerByGroupsId(id);

            if (groups.isEnabled()) {
                for (Seller seller: sellers) {
                    seller.setEnabled(true);
                    sellerRepository.save(seller);
                }
            } else {
                for (Seller seller: sellers) {
                    seller.setEnabled(false);
                    sellerRepository.save(seller);
                }
            }
            groupsService.save(groups);
        }

        return "redirect:/draw";
    }
    /**
     * Route to configure seller (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/seller/{id}")
    public String configurationSeller (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo Vandè sa pa egziste, itilize yon lòt");
            return "redirect:/seller";
        }
        Seller seller = sellerService.findSellerById(id);
        if(seller == null){
            model.addAttribute("error","Vandè sa pa egziste.");
        }else {
            seller.setEnabled(!seller.isEnabled());
            sellerRepository.save(seller);
        }

        return "redirect:/seller";
    }

    /**
     * Route to configure Enterprise (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/enterprise/{id}")
    public String configurationEnterprise (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo antrepriz sa pa egziste, itilize yon lòt");
            return "redirect:/enterprise";
        }
        Enterprise enterprise = enterpriseService.findEnterpriseById(id);
        if(enterprise == null){
            model.addAttribute("error","Antrepriz sa pa egziste.");
        }else {
            enterprise.setEnabled(!enterprise.isEnabled());
            enterpriseRepository.save(enterprise);
        }

        return "redirect:/enterprise";
    }

    /**
     * Route to configure combination (Block and UnBlock, Change Maximum price of a combination)
     *      * @return
     */
    @GetMapping("/combination")
    public String blockCombination(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("combinationTypes", combinationTypeService.findall());
        return "/configuration/configurationCombination";
    }

    /**
     * Route to configure shift (Change open and close date)
     *      * @return
     */
    @GetMapping("/shift")
    public String configureShift(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        return "/configuration/configurationShift";
    }

}
