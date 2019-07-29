package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.CombinationType;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Services.CombinationTypeService;
import com.b.r.loteriab.r.Services.ProductsService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dany on 09/05/2019.
 */
@Controller
@ControllerAdvice
@RequestMapping("/combinationType")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class CombinationTypeController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private CombinationTypeService combinationTypeService;

    @Autowired
    private ProductsService productsService;
    /*
  * Param id
  * Use to get the combination type
  * */

    @GetMapping("")
    public String index(Model model,  HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            return "/index/combinationType.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade tip kobinezon yo");
        return "access-denied";
    }

    @GetMapping("/create")
    public String createCombinationType(HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            model.addAttribute("products",productsService.findAll());
            model.addAttribute("combinationType", new CombinationType());
            return "/create/combinationType";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye tip kobinezon an");
        return "access-denied";
    }

    @PostMapping("/create")
    public String saveCombinationType (@ModelAttribute("combinationType") CombinationType combinationType, HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            combinationType.setEnterprise(enterprise);
            Result result = combinationTypeService.saveCombinationType(combinationType, enterprise);
            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/combinationType/create";
            }

            return "redirect:/combinationType";
        }
        model.addAttribute("error", "Ou pa ka anrejistre tip konbinezon an koz itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye tip kobinezon an");
        return "access-denied";
    }

    @GetMapping("/update/{id}")
    public String getCombinationType(@PathVariable("id")Long id, HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            if (id <= 0) {
                model.addAttribute("error", "Konbinezon sa pa egziste, cheche on lòt");
                return "404";
            }
            CombinationType combinationType = combinationTypeService.findCombinationTypeByIdAndEntepriseId(id, enterprise.getId());

            if (combinationType == null) {
                model.addAttribute("error", "Konbinezon sa pa egziste, cheche on lòt");
                return "404";
            }
            model.addAttribute("products", productsService.findAll());
            model.addAttribute("combinationType", combinationType);
            return "/update/combinationType";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye tip kobinezon an");
        return "access-denied";
    }

    @PostMapping("/update")
    public String updateCombinationType (@ModelAttribute("combinationType") CombinationType combinationType, @RequestParam("id") Long id, RedirectAttributes redirectAttributes,  HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            if (id > 0) {
                combinationType.setId(id);
            } else {
                redirectAttributes.addFlashAttribute("error", "Nimewo Tip Konbinezon sa pa bon, reeseye anko");
                return "redirect:/combinationType";
            }

            Result result =  combinationTypeService.updateCombinationType(combinationType);
            if(!result.isValid()){
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/combinationType/update/"+ combinationType.getId();
            }

            return "redirect:/combinationType";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye tip kobinezon an");
        return "access-denied";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDraw(HttpServletRequest request, Model model, @PathVariable("id") Long id){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Nimewo konbinezon sa pa egziste, antre on lòt");
                return "404";
            }

            Result result = combinationTypeService.deleteCombinationTypeIdAndEnterpriseId(id, enterprise.getId());

//            TODO: Create routine to delete combinationType

            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
            }

            return "redirect:/combinationType";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou elimne tip kobinezon an");
        return "access-denied";
    }

}
