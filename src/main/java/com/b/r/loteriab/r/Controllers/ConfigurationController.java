package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.*;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Enums.NotificationType;
import com.b.r.loteriab.r.Notification.Interface.AuditEventService;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import com.b.r.loteriab.r.Repository.*;
import com.b.r.loteriab.r.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 09/05/2019.
 */
@Controller
@ControllerAdvice
@RequestMapping("/configuration")
@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class ConfigurationController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private CombinationTypeService combinationTypeService;

    @Autowired
    private CombinationTypeRepository combinationTypeRepository;

    @Autowired
    private PosService posService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private PosRepository posRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private DrawService drawService;

    @Autowired
    private DrawRepository drawRepository;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private GroupsRepository groupRepository;

    @Autowired
    private AuditEventService service;

    /**
     * Route to configigure Pos Type (Block and UnBlock)
     * @param id
     * @return configuration
     */

    @GetMapping("/pos/{id}")
    public String configurationPos (Model model, @PathVariable Long id, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (id <= 0) {
                model.addAttribute("error", "Nimewo machin sa pa egziste, itilize yon lòt");
                return "redirect:/pos";
            }
            Pos pos = posService.findPosById(id, enterprise.getId());
            if (pos == null) {
                model.addAttribute("error", "Machin sa pa egziste.");
            } else {
                pos.setEnabled(!pos.isEnabled());
                Pos savedPos = posRepository.save(pos);

                SampleResponse sampleResponse = new SampleResponse();
                LastNotification last = new LastNotification();
                last.setChanged(true);
                last.setDate(new Date());
                last.setEnterpriseId(enterprise.getId());
                last.setType(NotificationType.PosBlocked.ordinal());
                sampleResponse.getBody().put("pos", savedPos);
                last.setSampleResponse(sampleResponse);
                service.sendMessage(sampleResponse, enterprise.getId(), last);

            }

            return "redirect:/pos";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou bloke machin sa");
        return "access-denied";
    }

    /**
     * Route to configigure Bank Type (Block and UnBlock)
     * @param id
     * @return configuration
     */

    @GetMapping("/bank/{id}")
    public String configurationBank (Model model, @PathVariable Long id, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (id <= 0) {
                model.addAttribute("error", "Nimewo bank sa pa egziste, itilize yon lòt");
                return "redirect:/bank";
            }
            Bank bank = bankService.findBankById(id, enterprise.getId());
            if (bank == null) {
                model.addAttribute("error", "Bank sa pa egziste.");
            } else {
                bank.setEnabled(!bank.isEnabled());
                bankRepository.save(bank);
            }

            return "redirect:/bank";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou bloke bank sa");
        return "access-denied";
    }

    /**
     * Route to configure user (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/{type}/user/{id}")
    public String configurationUser (Model model, @PathVariable("id") Long id, @PathVariable("type") Long type, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (id <= 0) {
                model.addAttribute("error", "Nimewo itilizatè sa pa egziste, itilize yon lòt");
                return "redirect:/user/"+ type;
            }
            Users users = usersService.findUser(id, enterprise.getId());
            if (users == null) {
                model.addAttribute("error", "Itilizatè sa pa egziste");
            } else {
                users.setEnabled(!users.isEnabled());
                userRepository.save(users);
            }

            return "redirect:/user/"+ type;
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou bloke itilizatè sa");
        return "access-denied";
    }

    /**
     * Route to configure draw (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/draw/{id}")
    public String configurationDraw (Model model, @PathVariable Long id, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (id <= 0) {
                model.addAttribute("error", "Nimewo Tiraj sa pa egziste, itilize yon lòt");
                return "redirect:/draw";
            }
            Draw draw = drawService.findDrawById(id, enterprise.getId());
            if (draw == null) {
                model.addAttribute("error", "Tiraj sa pa egziste");
            } else {
                draw.setEnabled(!draw.isEnabled());
                drawRepository.save(draw);
            }

            return "redirect:/draw";
        }
       model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou bloke tiraj sa");
        return "access-denied";
    }

    /**
     * Route to configure group (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/group/{id}")
    public String configurationGroup (Model model, @PathVariable Long id, HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (id <= 0) {
                model.addAttribute("error", "Nimewo Gwoup sa pa egziste, itilize yon lòt");
                return "redirect:/group";
            }
            Groups groups = groupsService.findGroupsById(id, enterprise.getId());
            if (groups == null) {
                model.addAttribute("error", "Gwoup sa pa egziste");
            } else {
                groups.setEnabled(!groups.isEnabled());
                List<Seller> sellers = sellerService.findAllSellerByGroupsId(id, enterprise.getId());

                if (groups.isEnabled()) {
                    for (Seller seller : sellers) {
                        seller.setEnabled(true);
                        sellerRepository.save(seller);
                    }
                } else {
                    for (Seller seller : sellers) {
                        seller.setEnabled(false);
                        sellerRepository.save(seller);
                    }
                }
                groupRepository.save(groups);
            }

            return "redirect:/group";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou bloke gwoup sa");
        return "access-denied";
    }
    /**
     * Route to configure seller (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/seller/{id}")
    public String configurationSeller (Model model, @PathVariable Long id,  HttpServletRequest request) {
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            if (id <= 0) {
                model.addAttribute("error", "Nimewo Vandè sa pa egziste, itilize yon lòt");
                return "redirect:/seller";
            }
            Seller seller = sellerService.findSellerById(id, enterprise.getId());
            if (seller == null) {
                model.addAttribute("error", "Vandè sa pa egziste.");
            } else {
                seller.setEnabled(!seller.isEnabled());
                sellerRepository.save(seller);
            }

            return "redirect:/seller";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou bloke vandè sa");
        return "access-denied";
    }

    /**
     * Route to configure Enterprise (Block and UnBlock)
     * @param id
     * @return
     */
    @GetMapping("/enterprise/{id}")
    public String configurationEnterprise (Model model, @PathVariable Long id) {

        if(id <=0){
            model.addAttribute("error","Nimewo antrepriz sa pa egziste, itilize yon lòt");
            return "redirect:/enterprise";
        }
        Enterprise enterprise = enterpriseService.findEnterpriseById(id);
        if(enterprise == null){
            model.addAttribute("error","Antrepriz sa pa egziste.");
        }else {
            enterprise.setEnabled(!enterprise.isEnabled());
            enterpriseRepository.save(enterprise);
        }

        return "redirect:/enterprise";
    }

    /**
     * Route to configure combination (Block and UnBlock, Change Maximum price of a combination)
     *      * @return
     */
    @GetMapping("/combination")
    public String blockAndChangePriceCombination(HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            model.addAttribute("combinationTypes", combinationTypeService.findallByEnterpriseId(enterprise.getId()));
            return "/configuration/configurationCombination";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou chanje oubyen bloke konbinezon yo");
        return "access-denied";
    }

    /**
     * Route to configure shift (Change open and close date)
     *      * @return
     */
    @GetMapping("/shift")
    public String configureShift(HttpServletRequest request, Model model){
        Enterprise enterprise = (Enterprise) request.getSession().getAttribute("enterprise");
        if (enterprise!= null) {
            String username = request.getSession().getAttribute("username").toString();
            Users user = usersService.findUserByUsernameAndEnterpriseId(username, enterprise.getId());
            model.addAttribute("user", user);

            return "/configuration/configurationShift";
        }
        model.addAttribute("error", "Itilizatè sa pa fè pati de kliyan nou yo, ou pa gen aksè pou ou chanje tip tiraj la");
        return "access-denied";
    }

}
