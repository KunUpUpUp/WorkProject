package com.seasugar.controller;

import com.seasugar.domain.Test;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    private List<Test> list = new ArrayList<>();

    @PostMapping("test")
    public void addList(@RequestBody Test test) {
        list.add(test);
        System.out.println("添加成功");
    }
}
