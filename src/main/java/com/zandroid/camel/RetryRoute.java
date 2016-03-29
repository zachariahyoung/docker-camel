package com.zandroid.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RetryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        errorHandler(deadLetterChannel("activemq:TEST"));

//        from("activemq:TRIGGER?transacted=true&concurrentConsumers=10").routeId("Trigger")
//                .onException(Exception.class)
//                    .maximumRedeliveries(7)
//                    .to("activemq:EXCEPTION")
//                    .markRollbackOnlyLast().end()
//                .transacted()
//                .bean("helloService")
//                .to("log:out");

        from("activemqtx:JPATX").routeId("TriggerTX")
                    .onException(UpperCaseException.class)
                    .handled(true)
                    .to("activemq:EXCEPTION")
                    .end()
                    .transacted()
                .bean("helloService")
                .to("log:out");
    }
}
