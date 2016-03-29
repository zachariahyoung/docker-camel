package com.zandroid.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ExceptionService implements Processor {

    @Override
    @Transactional
    public void process(Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);
        if (body.equals("small")) {
            log.warn(body);
            exchange.setException(new UpperCaseException());
        } else if (body.equals("big")) {
            log.warn(body);
            exchange.setException(new LowerCaseException());

            throw new LowerCaseException();

        }


    }
}
