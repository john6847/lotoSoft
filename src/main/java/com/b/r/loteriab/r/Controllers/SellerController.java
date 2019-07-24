package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.GroupsRepository;
import com.b.r.loteriab.r.Repository.UserRepository;
import com.b.r.loteriab.r.Services.*;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosService posService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private GroupsRepository groupsRepository;


    @GetMapping("")
    public String index(Model model,  HttpServletRequest request) {
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        return "/index/seller.index";
    }

    @GetMapping("create")
    public String createSeller(HttpServletRequest request,Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("usersSelect", usersService.findAllUsersByEnabled("ROLE_SELLER", true));
        model.addAttribute("allGroups", groupsService.findAll());
        model.addAttribute("pos", posService.findAllFreePosByEnabled(true));
        model.addAttribute("seller", new Seller());
        return "/create/seller";
    }

    @PostMapping("/create")
    public String saveSeller(@ModelAttribute("seller") Seller seller,
                             @RequestParam(value ="username", defaultValue = "") String username,
                             @RequestParam(value ="name", defaultValue = "") String sellerName,
                             @RequestParam(value = "password", defaultValue = "") String password,
                             @RequestParam(value = "haveAUser", defaultValue = "off") String haveUser,
                             @RequestParam(value = "haveAGroup", defaultValue = "off") String haveAGroup,
                             @RequestParam(value = "useMonthlyPayment", defaultValue = "off") String useMonthlyPayment,
                             HttpServletRequest request,
                             Model model, RedirectAttributes redirectAttributes){
        String name = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(name);
        model.addAttribute("user", user);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (haveAGroup.equals("off")) {
            seller.setGroups(null);
        }
        if (haveUser.equals("off")) {
            Users users = new Users();
            users.setName(sellerName);
            users.setUsername(username);
            users.setPassword(bCryptPasswordEncoder.encode(password));
            users.setEnabled(true);
            Role role = roleService.findRoleByName(Roles.ROLE_SELLER.name());
            List<Role> rols = new ArrayList<>();
            rols.add(role);
            users.setRoles(rols);
            Users resultingUser = userRepository.save(users);
            seller.setUser(resultingUser);
        }

        Result result = sellerService.saveSeller(seller, useMonthlyPayment);
        if( !result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/seller/create";
        }

        return "redirect:/seller";
    }

    @GetMapping("/update/{id}")
    public String getSeller(@PathVariable("id") Long id, HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error", "Vandè sa pa egziste, cheche on lot ");
            return "404";
        }

        Seller seller = sellerService.findSellerById(id);
        model.addAttribute("usersSelect", usersService.findAllUsersByEnabled("ROLE_SELLER", true));
        model.addAttribute("allGroups", groupsService.findAll());
        model.addAttribute("groups", sellerService.findAllSellers());
        model.addAttribute("pos", posService.findAllFreePosByEnabled(true));

        if(groupsRepository.findByParentSellerId(id)!=null){
            model.addAttribute("isParentSeller", true);
        } else {
            model.addAttribute("isParentSeller", false);
        }

        if(seller == null){
            model.addAttribute("error", "Vandè sa pa egziste, cheche on lot");
            return "404";
        }

        model.addAttribute("seller", seller);
        model.addAttribute("user", seller.getUser());
        return "/update/seller";
    }

    @PostMapping("/update")
    public String updateSeller(@ModelAttribute("seller") Seller seller,
                               @RequestParam(value = "haveAGroup", defaultValue = "off") String haveAGroup,
                               @RequestParam(value = "useMonthlyPayment", defaultValue = "off") String useMonthlyPayment,
                               @RequestParam("id") Long id,HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);



        if (haveAGroup.equals("off")) {
            seller.setGroups(null);
        }

        if (id > 0) {
            seller.setId(id);
        } else {
            redirectAttributes.addFlashAttribute("error", "Nimewo vandè a pa bon, reeseye anko");
            return "redirect:/seller";
        }

        Result result = sellerService.updateSeller(seller, useMonthlyPayment);
        if(!result.isValid() ){
            redirectAttributes.addFlashAttribute("error", result.getValue());
            return "redirect:/update/"+ seller.getId();
        }

        return "redirect:/seller";
    }

    @PostMapping("/delete/{id}")
    public  String deleteSeller(HttpServletRequest request,Model model,@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            redirectAttributes.addFlashAttribute("error", "Vande a pa egziste");
            return "redirect:/seller";
        }
        Result result = sellerService.deleteSellerById(id);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getValue());
        }

        return "redirect:/seller";
    }
}
