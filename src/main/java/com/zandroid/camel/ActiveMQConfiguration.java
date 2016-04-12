package com.zandroid.camel;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConfiguration {

    @Value("${producerBrokerURL}")
    private String producerBrokerURL;

    @Value("${consumerBrokerURL}")
    private String consumerBrokerURL;

    @Value("${maximumRedeliveryDelay}")
    private long maximumRedeliveryDelay;

    @Value("${maxConnections}")
    private int maxConnections;




    @Bean
    public ActiveMQComponent activemq() {
        // Connection Factory

        ActiveMQConnectionFactory activeMQConnectionFactory = activeMQConnectionFactory(producerBrokerURL);

        // Pooled Connection Factory

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(maxConnections);

        // ActiveMQ Component

        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(pooledConnectionFactory);
        activeMQComponent.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONNECTION);

        return activeMQComponent;
    }

    @Bean
    public ActiveMQComponent activemqtx()
    {
        // Connection Factory

        ActiveMQConnectionFactory activeMQConnectionFactory = activeMQConnectionFactory(consumerBrokerURL);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy());

        // Pooled Connection Factory

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(maxConnections);

        // ActiveMQ Component

        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(pooledConnectionFactory);
        activeMQComponent.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        activeMQComponent.setTransacted(true);


        return activeMQComponent;
    }



    private RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();

        //redeliveryPolicy.setBackOffMultiplier(2);
        //redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setMaximumRedeliveries(2);
        //redeliveryPolicy.setRedeliveryDelay(maximumRedeliveryDelay);

        return redeliveryPolicy;
    }

    private ActiveMQConnectionFactory activeMQConnectionFactory(String brokerURL) {
        System.out.println("SETTING UP AMQ WITH: " + brokerURL);
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);

       
        return activeMQConnectionFactory;
    }
}
