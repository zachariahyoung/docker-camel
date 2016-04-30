package com.zandroid.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ByeService implements Processor {

    @Override
    @Transactional
    public void process(Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);
        if (body.equals("Hello Upper")) {
            log.warn(body);
            //exchange.setException(new UpperCaseException(body));
            throw new UpperCaseException(body);
        } else if (body.equals("Hello Lower")) {
            log.warn(body);
            //exchange.setException(new LowerCaseException(body));
            throw new LowerCaseException(body);
        }
    }
}
