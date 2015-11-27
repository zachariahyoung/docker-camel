package com.zandroid.camel;

import org.apache.camel.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ExceptionTest  {
	
	@Handler
	public void exception() {
		
		
		throw new RuntimeException("test");
		
		//return "I'm Spring bean!";
	}

}
