package com.example.demo.controller;


import com.example.demo.dto.LoadInfo;
import com.example.demo.model.Chat;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.UserDetailsImp;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages-rest")
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserService userService;

    @PostMapping("/all")
    public List<Chat> getAll(@RequestBody LoadInfo info){


        return messageService.getAllByRoom(info.getChatRepo(),
                PageRequest.of(info.getPage(),info.getSize()));
    }


    @GetMapping ("/get/{id1}")
    public ChatRoom getAllChats(@PathVariable("id1") Long id1){

         java.lang.String  username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username" + username);

        Long id2 = userService.findByUsername(username).getId();
        ChatRoom chatRoom =  chatRoomService.getByTwoid(id1,id2);
        if (chatRoom!=null){
            chatRoom.sortChatsBytime();

            chatRoom.getChats().stream().forEach(item -> {
                if (item.getUser().getUsername().equals(username)){

                    item.setSent(true);
                }else {
                    item.setSent(false);
                }
                item.setRoom(null);});
            return chatRoom;
        }

        return null;
    }
}
