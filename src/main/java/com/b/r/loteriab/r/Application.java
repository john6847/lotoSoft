package com.b.r.loteriab.r;

import com.b.r.loteriab.r.Services.InitServices;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "com.b.r.loteriab.r")
public class Application {


	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		InitServices initServices= (InitServices) applicationContext.getBean("initServices");
		initServices.init();
	}

}
