package com.zandroid.camel;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CamelApplication extends FatJarRouter {
	
	@Override
	public void configure() {
		from("timer://trigger").transform().simple("ref:myBean").to("log:out", "activemq:queue:BRONTO.EVENT");
		
	}

	@Bean
	public String myBean() {
		return "I'm Spring bean!";
	}
}
