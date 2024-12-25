package com.seasugar.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LifeDemo {
    @Autowired
    private User user;

    public User getUser() {
        user.setName("zhukunpeng");
        return user;
    }
}
