package com.b.r.loteriab.r.Controllers;


import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Services.PosService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
@RequestMapping("/pos")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class PosController {
    @Autowired
    private PosService posService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            return "/index/pos.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade machin yo");
        return "access-denied";
    }


    @GetMapping("/create")
    public String createPos(HttpServletRequest request, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            model.addAttribute("pos", new Pos());
            return "/create/pos";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye machin sa");
        return "access-denied";
    }

    @PostMapping("/create")
    public String savePos(@ModelAttribute("pos") Pos pos, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            Result result = posService.savePos(pos, enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/pos/create";
            }
            return "redirect:/pos";
        }
        model.addAttribute("error", "Ou pa ka anrejistre machin sa a koz itilizatè ou sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye machin sa");
        return "access-denied";
    }

    @GetMapping("/update/{id}")
    public String updatePos(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Nimewo machin sa pa egziste, cheche on lòt");
                return "404";
            }

            Pos pos = posService.findPosById(id, enterprise.getId());

            if (pos == null) {
                model.addAttribute("error", "Machin sa pa egziste, cheche on lòt");
                return "404";
            }

            model.addAttribute("pos", pos);
            return "/update/pos";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye machin sa");
        return "access-denied";
    }

    @PostMapping("/update")
    public String updatePos(@ModelAttribute("pos") Pos pos, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            Result result = posService.updatePos(pos, enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/pos/update/" + pos.getId();
            }

            return "redirect:/pos";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye machin sa");
        return "access-denied";
    }

    @RequestMapping("/delete/{id}")
    public String deletePos(HttpServletRequest request, Model model, @PathVariable("id") Long id) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Machin sa pa agziste, antre on lòt");
                return "404";
            }
            Result result = posService.deletePosById(id, enterprise.getId());

            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
            }
            return "redirect:/pos";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou elimne machin sa");
        return "access-denied";
    }

}
