package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.CombinationType;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Services.CombinationTypeService;
import com.b.r.loteriab.r.Services.ProductsService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        return "/index/combinationType.index";
    }

    @GetMapping("/create")
    public String createCombinationType(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("products",productsService.findAll());
        model.addAttribute("combinationType", new CombinationType());
        return "/create/combinationType";
    }

    @PostMapping("/create")
    public String saveCombinationType (@ModelAttribute("combinationType") CombinationType combinationType, HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        Result result = combinationTypeService.saveCombinationType(combinationType);
        if(!result.isValid()){
            model.addAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/combinationType/create";
        }

        return "redirect:/combinationType";
    }

    @RequestMapping("/update/{id}")
    public String getCombinationType(@PathVariable("id")Long id, HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error", "Konbinezon sa pa egziste, cheche on lòt");
            return "404";
        }

        CombinationType combinationType = combinationTypeService.findCombinationTypeById(id);

        if(combinationType == null){
           model.addAttribute("error", "Konbinezon sa pa egziste, cheche on lòt");
            return "404";
        }
        model.addAttribute("products",productsService.findAll());
        model.addAttribute("combinationType", combinationType);
        return "/update/combinationType";
    }

    @PostMapping("/update")
    public String updateCombinationType (@ModelAttribute("combinationType") CombinationType combinationType, @RequestParam("id") Long id, RedirectAttributes redirectAttributes,  HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);
        if (id > 0) {
            combinationType.setId(id);
        } else {
            redirectAttributes.addFlashAttribute("error", "Nimewo Konbinezon sa pa bon, reeseye anko");
            return "redirect:/combinationType";
        }

        Result result =  combinationTypeService.updateCombinationType(combinationType);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/combinationType/update/"+ combinationType.getId();
        }

        return "redirect:/combinationType";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDraw(HttpServletRequest request, Model model, @PathVariable("id") Long id){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error", "Nimewo konbinezon sa pa egziste, antre on lòt");
            return "404";
        }

        Result result =  combinationTypeService.deleteCombinationTypeId(id);

        if(!result.isValid()){
            model.addAttribute("error", result.getLista().get(0).getMessage());
        }

        return "redirect:/combinationType";
    }

}
