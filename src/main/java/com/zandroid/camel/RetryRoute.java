package com.zandroid.camel;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RetryRoute extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        getContext().setTracing(true);

    //    errorHandler(transactionErrorHandler().maximumRedeliveries(1));

//        errorHandler(new TransactionErrorHandlerBuilder()
//                .maximumRedeliveries(1)
//        .setDeadLetter("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME")
//        );

//        TransactionErrorHandlerBuilder txErrorHandler = new TransactionErrorHandlerBuilder();
//        txErrorHandler.setDeadLetterUri("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME");
//
//        errorHandler(txErrorHandler.maximumRedeliveries(1));
//
//        errorHandler(deadLetterChannel("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME")
//                .useOriginalMessage()
//                );
//
//
//
//        errorHandler(transactionErrorHandler()
//                );


        onException(UpperCaseException.class)
                .maximumRedeliveries(2)
                .handled(true)
//                .to("log:UpperCaseException")
                .to("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME")
                .markRollbackOnlyLast();

//        onException(Exception.class)
//                .maximumRedeliveries(2)
//                .handled(true)
//                .to("log:UpperCaseException")
//                .to("activemq:ERROR.SERVER.Q.SUBDOMAIN.OBJECTNAME")
//                .markRollbackOnlyLast();



//        errorHandler(transactionErrorHandler()
//
//                    .disableRedelivery()
//                    .
//                    );


        from("activemqtx:SERVER.Q.SUBDOMAIN.OBJECTNAME").routeId(RetryRoute.class.getSimpleName())
                .transacted()
                .bean("helloService")
                .bean("byeService")

                .to("log:out");
    }
}
