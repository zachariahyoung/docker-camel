package com.zandroid.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQConfiguration;
import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactoryBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(ActiveMQProperties.class)
public class CamelApplication extends FatJarRouter {
//
//    @Autowired
//    private ActiveMQConnectionFactory test;

//    @Autowired
//    private ActiveMQProperties properties;
//
//    @Bean
//    @Primary
////    @DependsOn(value = "jmsConnectionFactory" )
//    public PooledConnectionFactory pooledConnectionFactory(ActiveMQProperties properties) {
//        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
//
//        pooledConnectionFactory
//                .setConnectionFactory(new ActiveMQConnectionFactoryFactory(properties)
//                        .createConnectionFactory(ActiveMQConnectionFactory.class));
//
//        return pooledConnection;
//
//    }

//
//    @Bean
//    @DependsOn(value = { "pooledConnectionFactory" })
//    public JmsTransactionManager jmsTransactionManager(PooledConnectionFactory pooledConnectionFactory){
//        return new JmsTransactionManager(pooledConnectionFactory);
//
//    }
//
//    @Bean
//    public ActiveMQComponent activeMQComponent(PooledConnectionFactory pooledConnectionFactory,JmsTransactionManager jmsTransactionManager ){
//
//        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
//
//        activeMQComponent.setConnectionFactory(pooledConnectionFactory);
//        activeMQComponent.setTransactionManager(jmsTransactionManager);
//
////        activeMQComponent.setBrokerURL("tcp://192.168.99.100:32774");
////        activeMQComponent.setUserName("admin");
////        activeMQComponent.setPassword("admin");
////        activeMQComponent.setUsePooledConnection(true);
//       activeMQComponent.setTransacted(true);
//
//        return activeMQComponent;
//    }




    @Override
    public void configure() throws Exception {


        errorHandler(deadLetterChannel("activemq:EXCEPTION").useOriginalMessage().maximumRedeliveries(1));

        from("activemq:TRIGGER").routeId("Trigger")
                .bean("helloService")
                .to("log:out");


//        from("activemq:TRIGGER").routeId("Trigger")
//                //  .transacted()
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
