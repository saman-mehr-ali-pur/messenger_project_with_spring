package com.example.demo.controller;


import com.example.demo.dto.LoadInfo;
import com.example.demo.model.Chat;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages-rest")
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/all")
    public List<Chat> getAll(@RequestBody LoadInfo info){


        return messageService.getAllByRoom(info.getChatRepo(),
                PageRequest.of(info.getPage(),info.getSize()));
    }
}
