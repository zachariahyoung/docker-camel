package com.zandroid.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.spring.boot.FatJarRouter;
import org.hibernate.exception.DataException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.JmsTransactionManager;

@SpringBootApplication
public class CamelApplication extends FatJarRouter {

    @Bean
    public ActiveMQComponent activemqtx(PooledConnectionFactory pooledConnectionFactory) {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setTransacted(true);
        activeMQComponent.setTransactionManager(new JmsTransactionManager(pooledConnectionFactory));
        return activeMQComponent;
    }


    @Override
    public void configure() throws Exception {


        from("activemqtx:TRIGGER").routeId("Trigger")
                .onException(Exception.class)
                    .maximumRedeliveries(7)
                    .to("activemq:EXCEPTION")
                    .markRollbackOnlyLast().end()
                .onException(UpperCaseException.class)
                    .handled(true)
                    .markRollbackOnlyLast()
                    .end()
                .transacted()
                .bean("helloService")
                .to("log:out");


//        from("activemq:TRIGGER").routeId("Trigger")
        //  .transacted()
//                //.filter(x -> x.getIn().getBody() != null)
//                //.wireTap("jms:orderAudit")
//                //.process(x -> System.out.println(x.getIn().getBody()))
//                //.recipientList(x ->  )
//                //.bean("exceptionTest")
//                // .throwException(new Exception())
//                .bean("helloService")
//                .to("log:out");

//        from("seda:COOL")
//                // .process(x -> System.out.println(x.getIn().getBody()))
//                //.recipientList(x ->  )
//                .to("log:out", "activemq:queue:TEST");


//        errorHandler(deadLetterChannel("seda:COOL")
//                .deadLetterHandleNewException(true)
//
//                .logNewException(true));
        //errorHandler(new TransactionErrorHandler().maximumRedeliveries(6));


//        errorHandler();

        //onException(RuntimeCamelException.class).continued(true);

//        from("timer://trigger")
//                .transform(simple("ref:myBean"))
//                .to("activemq:queue:TRIGGER");


//        from("file:target/inventory")
//                .log("Starting to process big file: ${header.CamelFileName}")
//                .split(body().tokenize("\n")).streaming()
//                .bean("exceptionTest")
//                .to("direct:update")
//                .end()
//                .log("Done processing big file: ${header.CamelFileName}");
//
//        from("direct:update")
//                .bean("exceptionTest");

//        ExecutorService threadPool = Executors.newFixedThreadPool(20);
//
//        from("jetty:http://localhost:8080/early").routeId("input")
//                .wireTap("direct:incoming")
//                .transform().constant("OK");
//        from("direct:incoming").routeId("process")
//                .convertBodyTo(String.class)
//                .log("Incoming ${body}")
//                .delay(3000)
//                .log("Processing done for ${body}")
//                .to("mock:result");
//    }
//

//        split(body().tokenize("\n")).streaming().parallelProcessing()
//                .bean(InventoryService.class, "csvToObject")
//                .to("direct:update")
//                .end()

//        from("file://riders/orders")
//                .log("We got incoming file ${file:name} containing: ${body}")
//                .bean(OrderCsvToXmlBean.class)
//                .to("jms:queue:orders");

    }

    @Bean
    public String myBean() {
        return "I'm Spring bean!";
    }


}
