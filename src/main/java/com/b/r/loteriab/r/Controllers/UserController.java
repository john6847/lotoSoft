package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Enums.Roles;
import com.b.r.loteriab.r.Model.Role;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Model.ViewModel.PasswordResetViewModel;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Repository.UserRepository;
import com.b.r.loteriab.r.Services.EnterpriseService;
import com.b.r.loteriab.r.Services.RoleService;
import com.b.r.loteriab.r.Services.SellerService;
import com.b.r.loteriab.r.Services.UsersService;
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
import java.util.List;

/**
 * Created by Dany on 09/05/2019.
 */
@Controller
@ControllerAdvice
@RequestMapping("/user")
@Secured({"ROLE_SUPER_ADMIN", "ROLE_SUPER_MEGA_ADMIN"})
public class UserController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    /*
     * Param id
     * Use to get the draw
     * */
    @GetMapping("/{type}")
    public String index(Model model, HttpServletRequest request, @PathVariable long type) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);


            if (type > 1)
                return "/index/user-super-admin.index";
            return "/index/user.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade Itilizatè  ou yo");
        return "access-denied";
    }

    @GetMapping("/{type}/password/reset")
    public String changePassword(HttpServletRequest request, @PathVariable int type, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (type <= 0) {
                type = 1;
            }

            if (type > 1) {
                model.addAttribute("users", usersService.findAllUserSuperAdmin());
            } else {
                model.addAttribute("users", usersService.findAllExceptSuperAdmin(enterprise.getId()));
            }

            model.addAttribute("type", type);
            model.addAttribute("passwordResetViewModel", new PasswordResetViewModel());
            return "/create/password-reset";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye itilizatè sa");
        return "access-denied";
    }

    @RequestMapping("/password/reset")
    public String saveUser(@ModelAttribute("passwordResetViewModel") PasswordResetViewModel vm,
                           HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            int type = Integer.parseInt(vm.getType());
            if (type <= 1) {
                type = 1;
            }

            if (vm.getUsers() == null) {
                redirectAttributes.addFlashAttribute("error", "Ou dwe antre itilizate a.");
                if (type > 1)
                    return "redirect:/user/2/password/reset";
                return "redirect:/user/1/password/reset";
            }
            if (!vm.getConfirmPassword().equals(vm.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "Modpas yo sipoze menm");
                if (type > 1)
                    return "redirect:/user/2/password/reset";
                return "redirect:/user/1/password/reset";
            }

            Users savedUser = userRepository.findUsersById(vm.getUsers().getId());
            if (savedUser == null) {
                redirectAttributes.addFlashAttribute("error", "Ititilizatè sa pa egziste.");
                if (type > 1)
                    return "redirect:/user/2/password/reset";
                return "redirect:/user/1/password/reset";
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            savedUser.setPassword(bCryptPasswordEncoder.encode(vm.getPassword()));
            userRepository.save(savedUser);

            if (type > 1) {
                return "redirect:/user/2";
            }
            return "redirect:/user/1";
        }
        model.addAttribute("error", "Ou pa ka chanje modpas itilizatè sa a koz itilizatè ou a pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye itilizatè sa");
        return "access-denied";
    }

    @GetMapping("/{type}/create")
    public String createUser(HttpServletRequest request, @PathVariable int type, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (type <= 0) {
                type = 1;
            }

            if (type == 2) {
                model.addAttribute("enterprises", enterpriseService.selectAllEnterpriseExcept());
                model.addAttribute("isSuperAdmin", true);
            } else {
                model.addAttribute("isSuperAdmin", false);
            }

            model.addAttribute("users", new Users());
            model.addAttribute("roles", roleService.findAllByEnterpriseId(enterprise.getId()));
            return "/create/user";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye itilizatè sa");
        return "access-denied";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("users") Users users,
                           @RequestParam(value = "confirmPassword") String confirmPassword,
                           @RequestParam(value = "isSuperAdmin", defaultValue = "off") String isSuperAdmin,
                           @RequestParam(value = "isAdmin", defaultValue = "off") String isAdmin,
                           @RequestParam(value = "isSeller", defaultValue = "off") String isSeller,
                           @RequestParam(value = "isSupervisor", defaultValue = "off") String isSupervisor,
                           @RequestParam(value = "isCollector", defaultValue = "off") String isCollector,
                           HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {

            if (!confirmPassword.equals(users.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "Modpas yo sipoze menm");
                if (isSuperAdmin.equals("on"))
                    return "redirect:/user/2/create";
                return "redirect:/user/1/create";
            }

            if (usersService.findUserByUsernameAndEnterpriseId(users.getUsername(), enterprise.getId()) != null) {
                redirectAttributes.addFlashAttribute("error", "non ititilizatè a egziste deja");
                if (isSuperAdmin.equals("on"))
                    return "redirect:/user/2/create";
                return "redirect:/user/1/create";
            }

            if (isSuperAdmin.equals("off")) {
                if (isAdmin.equals("off") && isSeller.equals("off") && isSupervisor.equals("off") && isCollector.equals("off")) {
                    redirectAttributes.addFlashAttribute("error", "Ou dwe chwazi o mwen on tip pou itilizatè a");
                    return "redirect:/user/1/create";
                }
                users.setRoles(roleService.getSelectedRoles(isAdmin, isSeller, isSupervisor, isCollector, enterprise.getId()));
            }


            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            Result result = usersService.saveUsers(users, isSuperAdmin, enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                if (isSuperAdmin.equals("on"))
                    return "redirect:/user/2/create";
                return "redirect:/user/1/create";
            }

            if (isSuperAdmin.equals("on")) {
                return "redirect:/user/2";
            }
            return "redirect:/user/1";
        }
        model.addAttribute("error", "Ou pa ka anrejistre itilizatè sa a koz itilizatè ou sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye itilizatè sa");
        return "access-denied";
    }

    @GetMapping("/{type}/update/{id}")
    public String getDraw(@PathVariable("id") Long id, @PathVariable("type") int type, HttpServletRequest request, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Nimewo Itilizatè sa pa egziste, cheche on lòt");
                return "404";
            }

            Users users = usersService.findUserById(id);

            if (type <= 0) {
                type = 1;
            }

            if (type == 2) {
                model.addAttribute("enterprises", enterpriseService.selectAllEnterpriseExcept());
                model.addAttribute("isSuperAdmin", true);
            } else {
                model.addAttribute("isSuperAdmin", false);
            }

            if (users == null) {
                model.addAttribute("error", "Itilizatè sa pa egziste, cheche on lòt");
                return "404";
            }

            model.addAttribute("roles", roleService.findAllByEnterpriseId(enterprise.getId()));
            model.addAttribute("users", users);
            return "/update/user";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye itilizatè sa");
        return "access-denied";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("users") Users users,
                             @RequestParam(value = "isAdmin", defaultValue = "off") String isAdmin,
                             @RequestParam(value = "isSuperAdmin", defaultValue = "off") String isSuperAdmin,
                             @RequestParam(value = "isSeller", defaultValue = "off") String isSeller,
                             @RequestParam(value = "isSupervisor", defaultValue = "off") String isSupervisor,
                             @RequestParam(value = "isCollector", defaultValue = "off") String isCollector,
                             @RequestParam("id") Long id, HttpServletRequest request,
                             Model model, RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (isSuperAdmin.equals("off")) {
                if (isSeller.equals("off") && isAdmin.equals("off") && isSupervisor.equals("off") && isCollector.equals("off")) {
                    redirectAttributes.addFlashAttribute("error", "Ou dwe chwazi o mwen on tip pou itilizatè a");
                    return "redirect:/user/1/update/" + user.getId();
                }
                users.setRoles(roleService.getSelectedRoles(isAdmin, isSeller, isSupervisor, isCollector, enterprise.getId()));
            }

            if (id > 0) {
                users.setId(id);
            } else {
                redirectAttributes.addFlashAttribute("error", "Nimewo itilizatè sa pa bon, reeseye ankò");
                if (isSuperAdmin.equals("on"))
                    return "redirect:/user/2";
                return "redirect:/user/1";
            }


            Result result = usersService.updateUsers(users, isSuperAdmin, enterprise.getId());
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                if (isSuperAdmin.equals("on"))
                    return "redirect:/user/2/update/" + user.getId();
                return "redirect:/user/1/update/" + user.getId();
            }

            if (isSuperAdmin.equals("on"))
                return "redirect:/user/2";
            return "redirect:/user/1";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye itilizatè sa");
        return "access-denied";
    }

    @RequestMapping("/{type}/delete/{id}")
    public String deleteUser(HttpServletRequest request,
                             Model model,
                             @PathVariable("id") Long id,
                             @PathVariable("type") int type) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (type <= 1){
                type = 1;
            } else{
                type = 2;
            }

            if (id <= 0) {
                model.addAttribute("error", "Nimewo Itilizatè sa pa egziste");
                return "redirect:/user/"+ type;
            }

            Users savedUser = usersService.findUserById(id);
            if (savedUser == null){
                model.addAttribute("error", "Itilizatè sa pa egziste");
                return "redirect:/user/"+ type;
            }

            Seller seller;
            if (type == 2)
                seller = sellerRepository.findSellerByUserIdAndDeletedFalse(savedUser.getId());
            else
                seller = sellerRepository.findSellerByUserIdAndDeletedFalseAndEnterpriseId(savedUser.getId(), enterprise.getId());

            if(seller != null){
                seller.setUser(null);
                sellerRepository.save(seller);
            }

            Result result = usersService.deleteUserById(id, enterprise.getId());
            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
            }
            if (type > 1)
                return "redirect:/user/2";
            return "redirect:/user/1";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou elimne itilizatè sa");
        return "access-denied";
    }


}
