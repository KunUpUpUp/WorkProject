package com.seasugar.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.seasugar.kafka.KafkaMessageProducer.sendMessage;

@RestController("kafka")
public class KafKaController {

    @PostMapping
    public String send(@RequestParam String topic, @RequestBody String msg) {
        sendMessage(topic, msg);
        return "发送成功";
    }
}
