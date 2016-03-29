package com.zandroid.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class HelloService {

    private HelloRepository helloRepository;

    @Autowired
    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Handler
    @Transactional
    public void save(String body) {
        Hello hello = new Hello(body);

        if (body.equals("zzz")) {
            log.warn("exception: UpperCaseException");
            throw new UpperCaseException();
        } else if (body.equals("yyy")) {
            log.warn("exception: LowerCaseException");
            throw new LowerCaseException();

        }

        helloRepository.save(hello);
    }
}
