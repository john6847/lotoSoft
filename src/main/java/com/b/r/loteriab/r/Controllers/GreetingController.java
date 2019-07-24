package com.b.r.loteriab.r.Controllers;

import com.b.r.loteriab.r.Model.AuditEvent;
import com.b.r.loteriab.r.Model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topics/event")
    public AuditEvent greeting(HelloMessage helloMessage) throws Exception {
        return new AuditEvent("greeting", "Hello," + helloMessage.getName() + "!");
    }
}