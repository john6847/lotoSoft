package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by Roman on 18/10/18.
 */
@Controller
public class IndexController {
    @Autowired
    private UsersService usersService;


    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView dashboard(Model model, Principal user, HttpServletRequest request){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if(user!=null && enterprise!=null){
            model.addAttribute("user", usersService.findUserByUsernameAndEnterpriseId(user.getName(),enterprise.getId()));
        }
        return new ModelAndView("index");
    }

}
