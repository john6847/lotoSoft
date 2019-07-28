package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.TenantContext;
import com.b.r.loteriab.r.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by Roman on 18/10/18.
 */
@Controller
public class IndexController {
    @Autowired
    private UsersService usersService;


    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)

    public ModelAndView paginaPrincipal(Model model, Principal user){
        if(user!=null){
            model.addAttribute("user", usersService.findUserByUsername(user.getName()));



//            System.out.println(TenantContext.getCurrentTenantId());
//            System.out.println( new TenantContext().getTenant());
//            System.out.println( new TenantContext().getScopeContext());
        }
        return new ModelAndView("index");
    }

}
