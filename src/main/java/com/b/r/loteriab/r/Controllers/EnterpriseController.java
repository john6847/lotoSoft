package com.b.r.loteriab.r.Controllers;


import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Services.*;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_SUPER_MEGA_ADMIN")
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CombinationTypeService combinationTypeService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("")
    public String index(Model model,  HttpServletRequest request) {
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        return "/index/enterprise.index";
    }


    @RequestMapping("/create")
    public String createEnterprise(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("enterprise", new Enterprise());
        return "/create/enterprise";
    }

    @PostMapping("/create")
    public String saveEnterprise(@ModelAttribute("enterprise") Enterprise enterprise,
                                 @RequestParam(value = "country", defaultValue = "") String country,
                                 @RequestParam(value = "city", defaultValue = "") String  city,
                                 @RequestParam(value = "sector", defaultValue = "") String  sector,
                                 @RequestParam(value = "street", defaultValue = "") String street,
                                 @RequestParam(value = "number") int number,
                                 @RequestParam(value = "phone", defaultValue = "") String phone,
                                 @RequestParam(value = "email", defaultValue = "") String email,
                                 @RequestParam(value = "bolet", defaultValue = "off") String  bolet,
                                 @RequestParam(value = "lotoTwaChif", defaultValue = "off") String  lotoTwaChif,
                                 @RequestParam(value = "lotoKatChif", defaultValue = "off") String lotoKatChif,
                                 @RequestParam(value = "opsyon", defaultValue = "off") String opsyon,
                                 @RequestParam(value = "maryaj", defaultValue = "off") String maryaj,
                                 @RequestParam(value = "extra", defaultValue = "off") String extra,
                                 @RequestParam(value = "admin", defaultValue = "off") String admin,
                                 @RequestParam(value = "seller", defaultValue = "off") String seller,
                                 @RequestParam(value = "recollector", defaultValue = "off") String recollector,
                                 @RequestParam(value = "supervisor", defaultValue = "off") String supervisor,
                                 HttpServletRequest request,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        enterprise.setAddress(enterpriseService.buildAddress (country, city, sector, street, number, phone, email));
        Result result = enterpriseService.saveEnterprise(enterprise);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/enterprise/create";
        }
        // create roles
        roleService.createRoleForEnterprise(enterprise.getName());// TODO: Save roles base on the roles chosen when creating the enterprise
        // Creatiing combinationTypes
        combinationTypeService.initCombinationTypeForEnterprise(enterprise.getName(),bolet, lotoTwaChif, lotoKatChif, opsyon, maryaj, extra);
        // creating combinations
        return "redirect:/enterprise";
    }

    @RequestMapping("/update/{id}")
    public String updateEnterprise(@PathVariable("id") Long id,HttpServletRequest request,Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error","Nimewo enterprise sa pa egziste, cheche on lot");
            return "404";
        }

        Enterprise enterprise = enterpriseService.findEnterpriseById(id);

        if(enterprise == null){
            model.addAttribute("error","Nimewo enterprise sa pa egziste, cheche on lot");
            return "404";
        }

        model.addAttribute("enterprise", enterprise);
        return "/update/enterprise";
    }

    @PostMapping("/update")
    public String updateEnterprise(@ModelAttribute("enterprise") Enterprise enterprise,HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        Result result = enterpriseService.updateEnterprise(enterprise);
        if(!result.isValid()){
            model.addAttribute("error", "Antrepriz la pa modifye, eseye ankò");
            return "redirect:/enterprise/update/"+ enterprise.getId();
        }

        return "redirect:/enterprise";
    }

    @RequestMapping("/delete/{id}")
    public String deleteEnterprise(HttpServletRequest request,Model model,@PathVariable("id") Long id){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error","Antrepriz sa pa egziste, antre on lòt");
            return "404";
        }
        Result result =  enterpriseService.deleteEnterpriseById(id);

        if(!result.isValid()){
            model.addAttribute("error", result.getLista().get(0).getMessage());
        }

        return "redirect:/enterprise";
    }

}
