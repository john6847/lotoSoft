package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Roman on 18/10/18.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Nom d'utilisateur ou Mot de passe incorrect.");
        }

        if (logout != null) {
            model.addObject("msg", "Session fermee correctement.");
        }
        model.setViewName("login");

        return model;

    }
}