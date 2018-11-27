package com.robin.testcontainersexample.controller;

import com.robin.testcontainersexample.model.Hello;
import com.robin.testcontainersexample.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostMapping("/hello")
    public ResponseEntity<String> hello(Hello hello) {
        helloService.saveHello(hello);
        return ResponseEntity.ok("Nice");
    }

    @GetMapping("hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Nice");
    }
}
