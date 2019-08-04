package com.b.r.loteriab.r.Controllers;


import com.b.r.loteriab.r.Model.Bank;
import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Repository.SellerRepository;
import com.b.r.loteriab.r.Services.BankService;
import com.b.r.loteriab.r.Services.PosService;
import com.b.r.loteriab.r.Services.SellerService;
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
@RequestMapping("/bank")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class BankController {
    @Autowired
    private BankService bankService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PosService posService;

    @Autowired
    private SellerRepository sellerRepository;

    @RequestMapping("")
    public String index(Model model,  HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);
            return "/index/bank.index";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou gade bank yo");
        return "access-denied";
    }


    @GetMapping("/create")
    public String createBank(HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            model.addAttribute("bank", new Bank());
            model.addAttribute("sellers", sellerRepository.selectAllSellersFreeFromBankByEnterpriseId(true, enterprise.getId()));
//            model.addAttribute("pos", posService.findAllFreePosFromBankByEnabled(true, enterprise.getId()));
            return "/create/bank";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye bank sa");
        return "access-denied";
    }

    @PostMapping("/create")
    public String saveBank(@ModelAttribute("bank") Bank bank, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            Result result = bankService.saveBank(bank, enterprise);
            if (!result.isValid()) {
                redirectAttributes.addFlashAttribute("error", result.getLista().get(0).getMessage());
                return "redirect:/bank/create";
            }
            return "redirect:/bank";
        }
        model.addAttribute("error", "Ou pa ka anrejistre bank sa a koz itilizatè ou sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou kreye bank sa");
        return "access-denied";
    }

    @GetMapping("/update/{id}")
    public String updateBank(@PathVariable("id") Long id,HttpServletRequest request,Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Nimewo bank sa pa egziste, cheche on lot");
                return "404";
            }

            Bank bank = bankService.findBankById(id, enterprise.getId());

            if (bank == null) {
                model.addAttribute("error", "Nimewo machin sa pa egziste, cheche on lot");
                return "404";
            }

            model.addAttribute("bank", bank);
            model.addAttribute("sellers", sellerService.findAllSellersByEnterpriseId(enterprise.getId()));
//            model.addAttribute("pos", posService.findAllFreePosFromBankByEnabled(true, enterprise.getId()));
            return "/update/bank";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye machin sa");
        return "access-denied";
    }

    @PostMapping("/update")
    public String updateBank(@ModelAttribute("bank") Bank bank,HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            Result result = bankService.updateBank(bank, enterprise);
            if (!result.isValid()) {
                model.addAttribute("error", "Bank la pa modifye, eseye ankò");
                return "redirect:/bank/update/" + bank.getId();
            }

            return "redirect:/bank";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou modifye bank sa");
        return "access-denied";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBank(HttpServletRequest request,Model model,@PathVariable("id") Long id){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            if (id <= 0) {
                model.addAttribute("error", "Bank sa pa agziste, antre on lot");
                return "404";
            }
            Result result = bankService.deleteBankById(id, enterprise.getId());

            if (!result.isValid()) {
                model.addAttribute("error", result.getLista().get(0).getMessage());
            }

            return "redirect:/bank";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou elimne bank sa");
        return "access-denied";
    }

}
