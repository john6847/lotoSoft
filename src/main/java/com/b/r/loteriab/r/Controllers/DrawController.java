package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Draw;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.NumberThreeDigitsRepository;
import com.b.r.loteriab.r.Repository.NumberTwoDigitsRepository;
import com.b.r.loteriab.r.Services.DrawService;
import com.b.r.loteriab.r.Services.ShiftService;
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
@RequestMapping("/draw")
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
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        return "/index/draw.index";
    }

    @RequestMapping("/create")
    public String createDraw(HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        model.addAttribute("draw", new Draw());
        model.addAttribute("shifts", shiftService.findAll());
        model.addAttribute("numberThreeDigits", numberThreeDigitsRepository.findAll());
        model.addAttribute("numberTwoDigits", numberTwoDigitsRepository.findAll());
        return "/create/draw";
    }


    @PostMapping("/create")
    public String saveDraw(@ModelAttribute("draw") Draw draw,
                           @RequestParam(value ="date", defaultValue = "") String date,
                           HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);



        Result result = drawService.saveDraw(draw);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/draw/create";
        }

        return "redirect:/draw";
    }

    @RequestMapping("/update/{id}")
    public String getDraw(@PathVariable("id")Long id, HttpServletRequest request, Model model){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error", "Nimewo tiraj sa pa egziste, cheche on lòt");
            return "404";
        }

        Draw draw = drawService.findDrawById(id);

        if(draw == null){
            model.addAttribute("error", "Tiraj sa pa egziste, cheche on lòt");
            return "404";
        }

        model.addAttribute("draw", draw);
        model.addAttribute("shifts", shiftService.findAll());
        model.addAttribute("numberThreeDigits", numberThreeDigitsRepository.findAll());
        model.addAttribute("numberTwoDigits", numberTwoDigitsRepository.findAll());
        return "/update/draw";
    }

    @PostMapping("/update")
    public String updateDraw(@ModelAttribute("draw") Draw draw, @RequestParam("id") Long id, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if (id > 0) {
            draw.setId(id);
        } else {
            model.addAttribute("error", "Nimewo tiraj sa pa bon, reeseye anko");
            return "redirect:/draw";
        }

        Result result = drawService.updateDraw(draw);
        if(!result.isValid()){
            redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
            return "redirect:/draw/update/"+ draw.getId();
        }

        return "redirect:/draw";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDraw(HttpServletRequest request, Model model, @PathVariable("id") Long id){
        String username = request.getSession().getAttribute("username").toString();
        Users user = usersService.findUserByUsername(username);
        model.addAttribute("user", user);

        if(id <= 0){
            model.addAttribute("error", "Nimewo tiraj sa pa egziste, antre on lòt");
            return "404";
        }

        Result result = drawService.deleteDrawById(id);
        if(!result.isValid()){
            model.addAttribute("error", result.getLista().get(0).getMessage());
        }
        return "redirect:/draw";
    }

}
