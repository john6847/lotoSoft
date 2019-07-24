package com.b.r.loteriab.r.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Roman on 18/10/18.
 */
@Controller
@RequestMapping("/error")
public class ErrorHandlingController {

    @GetMapping(value = "/access-denied")
    public String accessDenied(){
        return "access-denied";
    }
}