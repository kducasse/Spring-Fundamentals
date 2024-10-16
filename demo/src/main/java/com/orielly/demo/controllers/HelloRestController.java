package com.orielly.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// A RestController we will return an object, and that object will get serialized into a JSON data
// and dumped into the output stream automatically
@RestController
public class HelloRestController {

    @GetMapping("/rest")
    public Greeting greet(@RequestParam(defaultValue = "World") String name) {
        return new Greeting("Hello, " + name + "!");
    }

}

record Greeting(String message) {}