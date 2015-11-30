package com.zandroid.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableTransactionManagement
public class CamelApplication extends FatJarRouter {

    @Override
    public void configure() throws Exception {

        // onException(RuntimeCamelException.class).to("activemq:queue:EXCEPTION");

        errorHandler(deadLetterChannel("activemq:queue:EXCEPTION"));

//        errorHandler();

        //onException(RuntimeCamelException.class).continued(true);

        from("timer://trigger")
                .transform(simple("ref:myBean"))

                .to("activemq:queue:TRIGGER");

        from("activemq:TRIGGER").routeId("Trigger").tracing()
                //.throwException(new RuntimeException("test"))
                //.filter(x -> x.getIn().getBody() != null)
                //.wireTap("jms:orderAudit")
                //.process(x -> System.out.println(x.getIn().getBody()))
                //.recipientList(x ->  )
                //.bean("exceptionTest")

                .to("seda:COOL");

        from("seda:COOL")
               // .process(x -> System.out.println(x.getIn().getBody()))
                //.recipientList(x ->  )
                .to("log:out", "activemq:queue:TEST");

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

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

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
