package com.zandroid.camel;

import org.apache.camel.CamelExchangeException;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FirstRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(RuntimeCamelException.class)
                .to("activemq:queue:EXCEPTION");

        from("timer://trigger")
                .transform()
                .simple("ref:myBean")
                .to("activemq:queue:TRIGGER");

        from("activemq:TRIGGER")
                .bean("exceptionTest")
                .to("log:out", "activemq:queue:TEST");


    }

}
