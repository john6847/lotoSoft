package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.GroupsRepository;
import com.b.r.loteriab.r.Repository.UserRepository;
import com.b.r.loteriab.r.Services.*;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/seller")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PosService posService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("")
    public String index(Model model,  HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            return "/index/seller.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade vandè ou yo");
        return "access-denied";
    }

    @GetMapping("create")
    public String createSeller(HttpServletRequest request,Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            model.addAttribute("usersSelect", usersService.findAllUsersByEnabled("ROLE_SELLER", true, enterprise.getId()));
            model.addAttribute("allGroups", groupsService.findAllGroups(enterprise.getId()));
            model.addAttribute("pos", posService.findAllFreePosByEnabled(true, enterprise.getId()));
            model.addAttribute("seller", new Seller());
            return "/create/seller";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye vandè sa");
        return "access-denied";
    }

    @PostMapping("/create")
    public String saveSeller(@ModelAttribute("seller") Seller seller,
                             @RequestParam(value ="username", defaultValue = "") String username,
                             @RequestParam(value ="name", defaultValue = "") String sellerName,
                             @RequestParam(value = "password", defaultValue = "") String password,
                             @RequestParam(value = "confirmPassword") String confirmPassword,
                             @RequestParam(value = "haveAUser", defaultValue = "off") String haveUser,
                             @RequestParam(value = "haveAGroup", defaultValue = "off") String haveAGroup,
                             @RequestParam(value = "useMonthlyPayment", defaultValue = "off") String useMonthlyPayment,
                             HttpServletRequest request,
                             Model model, RedirectAttributes redirectAttributes){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String name = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(name, enterprise.getId());
            model.addAttribute("user", user);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            if (!confirmPassword.equals(password)) {
                redirectAttributes.addFlashAttribute("error", "Modpas yo sipoze menm");
                return "redirect:/seller/create";
            }

            if (haveAGroup.equals("off")) {
                seller.setGroups(null);
            }

            Users users = new Users();
            if (haveUser.equals("off")) {
                users.setName(sellerName);
                users.setUsername(username);
                users.setPassword(bCryptPasswordEncoder.encode(password));
            }

            Result result = sellerService.saveSeller(seller, useMonthlyPayment, haveUser, users, enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/seller/create";
            }

            return "redirect:/seller";
        }
        model.addAttribute("error", "Ou pa ka anrejistre itilizatè sa a koz itilizatè ou sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye vandè sa");
        return "access-denied";
    }

    @GetMapping("/update/{id}")
    public String getSeller(@PathVariable("id") Long id, HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Vandè sa pa egziste, cheche on lot ");
                return "404";
            }

            Seller seller = sellerService.findSellerById(id, enterprise.getId());
            model.addAttribute("usersSelect", usersService.findAllUsersByEnabled("ROLE_SELLER", true, enterprise.getId()));
            model.addAttribute("allGroups", groupsService.findAllGroups(enterprise.getId()));
            model.addAttribute("groups", sellerService.findAllSellersByEnterpriseId(enterprise.getId()));
            model.addAttribute("pos", posService.findAllFreePosByEnabled(true, enterprise.getId()));

            if (groupsRepository.findByParentSellerIdAndEnterpriseId(id,enterprise.getId()) != null) {
                model.addAttribute("isParentSeller", true);
            } else {
                model.addAttribute("isParentSeller", false);
            }

            if (seller == null) {
                model.addAttribute("error", "Vandè sa pa egziste, cheche on lot");
                return "404";
            }

            model.addAttribute("seller", seller);
            return "/update/seller";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye vandè sa");
        return "access-denied";
    }

    @PostMapping("/update")
    public String updateSeller(@ModelAttribute("seller") Seller seller,
                               @RequestParam(value = "haveAGroup", defaultValue = "off") String haveAGroup,
                               @RequestParam(value = "useMonthlyPayment", defaultValue = "off") String useMonthlyPayment,
                               @RequestParam("id") Long id,HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
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

            Result result = sellerService.updateSeller(seller, useMonthlyPayment, enterprise.getId());
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getValue());
                return "redirect:/update/" + seller.getId();
            }

            return "redirect:/seller";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye vandè sa");
        return "access-denied";
    }

    @DeleteMapping("/delete/{id}")
    public  String deleteSeller(HttpServletRequest request,Model model,@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                redirectAttributes.addFlashAttribute("error", "Vandè a pa egziste");
                return "redirect:/seller";
            }
            Result result = sellerService.deleteSellerById(id, enterprise.getId());
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getValue());
            }

            return "redirect:/seller";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou elimne vandè sa");
        return "access-denied";
    }

}
