package com.zandroid.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;

@SpringBootApplication
public class CamelApplication extends FatJarRouter {

    @Bean
    @Primary
    public PooledConnectionFactory pooledConnectionFactory(ConnectionFactory jmsConnectionFactory) {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(jmsConnectionFactory);
        return pooledConnectionFactory;

    }


    @Bean
    public ActiveMQComponent activemqtx(ConnectionFactory pooledConnectionFactory) {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setTransacted(true);
        activeMQComponent.setTransactionManager(new JmsTransactionManager(pooledConnectionFactory));
        return activeMQComponent;
    }


    @Override
    public void configure() throws Exception {


        from("activemq:TRIGGER?transacted=true&concurrentConsumers=10").routeId("Trigger")
                .onException(Exception.class)
                    .maximumRedeliveries(7)
                    .to("activemq:EXCEPTION")
                    .markRollbackOnlyLast().end()
                .transacted()
                .bean("helloService")
                .to("log:out");

        from("activemqtx:TRIGGERTX").routeId("TriggerTX")
                .onException(Exception.class)
                    .maximumRedeliveries(7)
                    .to("activemq:EXCEPTION")
                    .markRollbackOnlyLast().end()
                .transacted()
                .bean("helloService")
                .to("log:out");



    }

    @Bean
    public String myBean() {
        return "I'm Spring bean!";
    }


}
