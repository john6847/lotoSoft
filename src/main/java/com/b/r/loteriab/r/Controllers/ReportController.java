package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Repository.*;
import com.b.r.loteriab.r.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dany on 09/05/2019.
 */
@Controller
@ControllerAdvice
@RequestMapping("/report")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class ReportController {
    @Autowired
    private UsersService usersService;
    /**
     * Route to Report
     * @param
     * @return report
     */

    @GetMapping("/sales")
    public String configureShift(HttpServletRequest request, Model model) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise != null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            return "/Report/report-sales.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade enfomasyon rapò sa");
        return "access-denied";
    }

}
