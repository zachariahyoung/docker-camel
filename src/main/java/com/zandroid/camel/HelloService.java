package com.zandroid.camel;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HelloService {

    private HelloRepository helloRepository;

    @Autowired
    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Handler
    @Transactional
    public void save(String body){
        Hello hello = new Hello(body);

        helloRepository.save(hello);
    }
}
