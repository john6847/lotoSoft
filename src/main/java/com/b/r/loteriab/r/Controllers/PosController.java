package com.b.r.loteriab.r.Controllers;


import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Services.PosService;
import com.b.r.loteriab.r.Services.UsersService;
import com.b.r.loteriab.r.Validation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
@RequestMapping("/pos")
public class PosController {
    @Autowired
    private PosService posService;

    @Autowired
    private UsersService usersService;


    @RequestMapping("")
    public String index(Model model,  HttpServletRequest request) {
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("allPos", posService.findAllPos());
        return "/index/pos.index";
    }


    @RequestMapping("/create")
    public String createPos(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("pos", new Pos());
        return "/create/pos";
    }

    @PostMapping("/create")
    public String savePos(@ModelAttribute("pos") Pos pos, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        Result result = posService.savePos(pos);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/pos/create";
        }
        model.addAttribute("allPos", posService.findAllPos());
        return "redirect:/pos";
    }

    @RequestMapping("/update/{id}")
    public String updatePos(@PathVariable("id") Long id,HttpServletRequest request,Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error","Nimewo pos sa pa egziste, cheche on lot");
            return "404";
        }

        Pos pos = posService.findPosById(id);

        if(pos == null){
            model.addAttribute("error","Nimewo pos sa pa egziste, cheche on lot");
            return "404";
        }

        model.addAttribute("pos", pos);
        return "/update/pos";
    }

    @PostMapping("/update")
    public String updatePos(@ModelAttribute("pos") Pos pos,HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        Result result = posService.updatePos(pos);
        if(!result.isValid()){
            model.addAttribute("error", "Pos la pa modifye, eseye ankò");
            return "redirect:/pos/update/"+ pos.getId();
        }

        model.addAttribute("allPos", posService.findAllPos());
        return "redirect:/pos";
    }

    @RequestMapping("/delete/{id}")
    public String deletePos(HttpServletRequest request,Model model,@PathVariable("id") Long id){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error","Pos sa pa agziste, antre on lot");
            return "404";
        }
        Result result =  posService.deletePosById(id);

        if(!result.isValid()){
            model.addAttribute("error", result.getLista().get(0).getMessage());
        }


        return "redirect:/pos";
    }
}
