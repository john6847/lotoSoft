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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableWebMvc
@EntityScan(basePackages = "com.b.r.loteriab.r")
public class Application {


	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		System.out.println("************************* "+TimeZone.getDefault()+"***************");
		System.out.println("************************* "+new Date() +"***************");
		InitServices initServices= (InitServices) applicationContext.getBean("initServices");
		initServices.init();
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("America/Port-au-Prince"));
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(Application.class);
//	}

	//	https://www.codota.com/code/java/methods/java.util.TimeZone/setDefault
//	https://howtodoinjava.com/java/date-time/convert-date-time-to-est-est5edt/
}
