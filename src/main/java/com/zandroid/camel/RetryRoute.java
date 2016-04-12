package com.zandroid.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RetryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        getContext().setTracing(true);

        errorHandler(deadLetterChannel("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME")
                .useOriginalMessage());

//        errorHandler(transactionErrorHandler().maximumRedeliveries(6));


//        onException(UpperCaseException.class)
//                .maximumRedeliveries(2)
//                .to("log:UpperCaseException")
//                .to("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME")
//                .markRollbackOnlyLast();



        from("activemqtx:SERVER.Q.SUBDOMAIN.OBJECTNAME").routeId(RetryRoute.class.getSimpleName())
                .transacted()
                .bean("helloService")
                .bean("byeService")
                .to("log:out");
    }
}
