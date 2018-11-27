package com.robin.testcontainersexample.service;

import com.robin.testcontainersexample.model.Hello;
import com.robin.testcontainersexample.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloService {

    private HelloRepository helloRepository;

    @Autowired
    public HelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    public void saveHello(Hello hello) {
        helloRepository.save(hello);
    }
}
