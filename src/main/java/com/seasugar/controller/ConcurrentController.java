package com.seasugar.controller;

import com.seasugar.service.ConcurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("concurrent")
public class ConcurrentController {

    @Autowired
    private ConcurrentService concurrentService;

    @GetMapping
    public void addService() {
        concurrentService.add();
    }
}
