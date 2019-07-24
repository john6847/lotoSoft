package com.b.r.loteriab.r.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 16/9/2017.
 *
 * A simple scheduler that tries to ping the web client every 10 seconds via web-socket
 */
@Component
public class WebSocketPingScheduler {

   @Autowired
   private AuditEventService service;

   private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   @Scheduled(fixedRate = 10000)
   public void webSocketPing() {
      service.sendMessage("ping", "web-socket-ping", "ping at time " + dateFormat.format(new Date()));
   }
}
