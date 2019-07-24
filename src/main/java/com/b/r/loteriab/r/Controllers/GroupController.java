package com.b.r.loteriab.r.Controllers;


import com.b.r.loteriab.r.Model.Address;
import com.b.r.loteriab.r.Model.Groups;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Services.GroupsService;
import com.b.r.loteriab.r.Services.SellerService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupsService groupService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;


    @RequestMapping("")
    public String index(Model model,  HttpServletRequest request) {
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);
        return "/index/group.index";
    }


    @RequestMapping("/create")
    public String createGroup(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(groupService.findAllGroups().size()<= 0){
            model.addAttribute("sellers", sellerService.findAllSellers());
        }else {
            model.addAttribute("sellers", sellerService.selectAllSellers());
        }
        model.addAttribute("group", new Groups());
        return "/create/group";
    }

    @PostMapping("/create")
    public String saveGroup(@ModelAttribute("group") Groups group,
                            @RequestParam(value = "country", defaultValue = "") String country,
                            @RequestParam(value = "city", defaultValue = "") String city,
                            @RequestParam(value = "sector", defaultValue = "") String sector,
                            @RequestParam(value = "phone", defaultValue = "") String phone,
                            HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        group.setAddress(createAddres(country, city, sector, phone));
        Result result = groupService.save(group);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/group/create";
        }
        model.addAttribute("allGroup", groupService.findAllGroups());
        return "redirect:/group";
    }

    @RequestMapping("/delete/{id}")
    public String deleteGroup(HttpServletRequest request,
                              Model model,@PathVariable("id") Long id,
                              RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            redirectAttributes.addFlashAttribute("error","Group sa pa agziste, antre on lot");
            return "redirect:/group";
        }
        Result result =  groupService.deleteGroupsById(id);
        List<Seller> sellers = sellerService.findAllSellerByGroupsId(id);

        for (Seller seller: sellers) {
            if (seller.getGroups()!= null) {
                if (seller.getGroups().getId().equals(id)){
                    seller.setGroups(null);
                    sellerRepository.save(seller);
                }
            }
        }

        if(!result.isValid()){
            model.addAttribute("error", result.getLista().get(0).getMessage());
        }

        return "redirect:/group";
    }

    private Address createAddres( String country, String city, String sector,String phone){
        Address address = null;
        if (!country.isEmpty()){
            address = new Address();
            address.setCountry(country);
        }
        if (!city.isEmpty()){
            if (address == null) {
                address = new Address();
            }
            address.setCity(city);
        }
        if (!sector.isEmpty()){
            if (address == null) {
                address = new Address();
            }
            address.setSector(sector);
        }

        if (!phone.isEmpty()){
            if (address == null) {
                address = new Address();
            }
            address.setPhone(phone);
        }

        return address;
    }
}
