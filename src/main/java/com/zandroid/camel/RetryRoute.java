package com.zandroid.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.spi.TransactionErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class RetryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        errorHandler(deadLetterChannel("activemq:TEST").useOriginalMessage().
//                disableRedelivery());







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
                    .maximumRedeliveries(2)
                    .handled(true)
                    .to("activemq:EXCEPTION")
                    .markRollbackOnlyLast()
                    .end()
                .transacted()
                .to("log:before")
                .bean("helloService")
                .bean("exceptionService")
                .to("log:out");
    }
}
