package com.b.r.loteriab.r.Controllers;


import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Groups;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.AddressRepository;
import com.b.r.loteriab.r.Repository.GroupsRepository;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Services.GroupsService;
import com.b.r.loteriab.r.Services.SellerService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/group")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class GroupController {
    @Autowired
    private GroupsService groupService;

    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private UsersService usersService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            return "/index/group.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade gwoup yo");
        return "access-denied";
    }

    @GetMapping("/create")
    public String createGroup(HttpServletRequest request, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            if (groupService.findAllGroups(enterprise.getId()).size() <= 0) {
                model.addAttribute("sellers", sellerService.findAllSellersByEnterpriseId(enterprise.getId())); // TODO: Change
            } else {
                model.addAttribute("sellers", sellerService.selectAllSellers(enterprise.getId())); // TODO: Change
            }
            model.addAttribute("group", new Groups());
            return "/create/group";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade gwoup yo");
        return "access-denied";
    }

    @PostMapping("/create")
    public String saveGroup(@ModelAttribute("group") Groups group,
                            @RequestParam(value = "country", defaultValue = "") String country,
                            @RequestParam(value = "city", defaultValue = "") String city,
                            @RequestParam(value = "sector", defaultValue = "") String sector,
                            @RequestParam(value = "phone", defaultValue = "") String phone,
                            @RequestParam(value = "region", defaultValue = "") String region,
                            HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            group.setAddress(groupService.createAddres(country, city, sector, phone));
            Result result = groupService.save(group, enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/group/create";
            }
            model.addAttribute("allGroup", groupService.findAllGroups(enterprise.getId()));
            return "redirect:/group";
        }
        model.addAttribute("error", "Ou pa ka anrejistre gwoup an koz itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye gwoup an");
        return "access-denied";
    }

    @RequestMapping("/delete/{id}")
    public String deleteGroup(HttpServletRequest request,
                              Model model, @PathVariable("id") Long id,
                              RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            if (id <= 0) {
                redirectAttributes.addFlashAttribute("error", "Group sa pa egziste, antre on lot");
                return "redirect:/group";
            }

            List<Seller> sellers = sellerService.findAllSellerByGroupsId(id, enterprise.getId());

            for (Seller seller : sellers) {
                if (seller.getGroups() != null) {
                    if (seller.getGroups().getId().equals(id)) {
                        seller.setGroups(null);
                        sellerRepository.save(seller);
                    }
                }
            }

            Groups groups = groupService.findGroupsById(id, enterprise.getId());
            long addressId = 0L;
            if (groups.getAddress() != null) {
                addressId = groups.getAddress().getId();
            }
            groups.setAddress(null);
            groups.setParentSeller(null);
            groupsRepository.save(groups);
            addressRepository.deleteById(addressId);

            Result result = groupService.deleteGroupsById(id, enterprise.getId());
            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
            }

            return "redirect:/group";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye gwoup an");
        return "access-denied";
    }

}
