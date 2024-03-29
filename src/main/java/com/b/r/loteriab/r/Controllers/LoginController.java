package com.b.r.loteriab.r.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
            model.addObject("error", "Itilizatè oubyen Modpas oubyen non antrepriz la pa bon.");
        }

        if (logout != null) {
            model.addObject("msg", "Sesyon an fèmen korèkteman.");
        }
        model.setViewName("login");

        return model;
    }
}