package com.zandroid.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HelloService implements Processor {

    private HelloRepository helloRepository;

    @Autowired
    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }


    @Override
    @Transactional
    public void process(Exchange exchange) throws Exception {

        log.info("entering Hello Service");
        Message in = exchange.getIn();
        String body = in.getBody(String.class);
        Hello hello = new Hello(body);
        helloRepository.save(hello);
        in.setBody("Hello " + in.getBody());


    }
}
