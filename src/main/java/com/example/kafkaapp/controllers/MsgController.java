package com.example.kafkaapp.controllers;

import com.example.kafkaapp.dto.Address;
import com.example.kafkaapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
public class MsgController {

    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<Long, UserDto> kafkaTemplate;

    @PostMapping
//    public void sendMessage(String id, String msg) {
    public void sendMessage(Long id, UserDto msg) {
        msg.setAddress(new Address("RUS","Krd","Krasnaya",5L,1L));
        ListenableFuture<SendResult<Long,UserDto>> future = kafkaTemplate.send("topic", id, msg);
//        ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send("topic", id, msg);
        future.addCallback(System.out::println,System.err::println);
        kafkaTemplate.flush();
    }
}
