package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Draw;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.NumberThreeDigitsRepository;
import com.b.r.loteriab.r.Repository.NumberTwoDigitsRepository;
import com.b.r.loteriab.r.Services.DrawService;
import com.b.r.loteriab.r.Services.ShiftService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dany on 09/05/2019.
 */
@Controller
@ControllerAdvice
@RequestMapping("/draw")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class DrawController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private DrawService drawService;

    @Autowired
    private NumberThreeDigitsRepository numberThreeDigitsRepository;

    @Autowired
    private NumberTwoDigitsRepository numberTwoDigitsRepository;

    @Autowired
    private ShiftService shiftService;
    /*
  * Param id
  * Use to get the draw
  * */
    @GetMapping("")
    public String index(Model model,  HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            return "/index/draw.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade tiraj yo");
        return "access-denied";
    }

    @GetMapping("/create")
    public String createDraw(HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            model.addAttribute("draw", new Draw());
            model.addAttribute("shifts", shiftService.findAllByEnterpriseId(enterprise.getId()));
            model.addAttribute("numberThreeDigits", numberThreeDigitsRepository.findAll());
            model.addAttribute("numberTwoDigits", numberTwoDigitsRepository.findAll());
            return "/create/draw";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye tiraj sa");
        return "access-denied";
    }


    @PostMapping("/create")
    public String saveDraw(@ModelAttribute("draw") Draw draw,
                           @RequestParam(value ="date", defaultValue = "") String date,
                           HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            Result result = drawService.saveDraw(draw,enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/draw/create";
            }

            return "redirect:/draw";
        }
        model.addAttribute("error", "Ou pa ka anrejistre tiraj sa a koz itilizatè ou sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye tiraj sa");
        return "access-denied";
    }

    @GetMapping("/update/{id}")
    public String getDraw(@PathVariable("id")Long id, HttpServletRequest request, Model model, FilterChain filterChain){

        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Nimewo tiraj sa pa egziste, cheche on lòt");
                return "404";
            }

            Draw draw = drawService.findDrawById(id, enterprise.getId());

            if (draw == null) {
                model.addAttribute("error", "Tiraj sa pa egziste, cheche on lòt");
                return "404";
            }

            model.addAttribute("draw", draw);
            model.addAttribute("shifts", shiftService.findAllByEnterpriseId(enterprise.getId()));
            model.addAttribute("numberThreeDigits", numberThreeDigitsRepository.findAll());
            model.addAttribute("numberTwoDigits", numberTwoDigitsRepository.findAll());
            return "/update/draw";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye tiraj sa");
        return "access-denied";
    }

    @PostMapping("/update")
    public String updateDraw(@ModelAttribute("draw") Draw draw, @RequestParam("id") Long id, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id > 0) {
                draw.setId(id);
            } else {
                model.addAttribute("error", "Nimewo tiraj sa pa bon, reeseye anko");
                return "redirect:/draw";
            }

            Result result = drawService.updateDraw(draw, enterprise.getId());
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/draw/update/" + draw.getId();
            }

            return "redirect:/draw";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye itilizatè sa");
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
                model.addAttribute("error", "Nimewo tiraj sa pa egziste, antre on lòt");
                return "404";
            }

            Result result = drawService.deleteDrawById(id, enterprise.getId());
            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
            }
            return "redirect:/draw";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou elimne tiraj  sa");
        return "access-denied";
    }

}
