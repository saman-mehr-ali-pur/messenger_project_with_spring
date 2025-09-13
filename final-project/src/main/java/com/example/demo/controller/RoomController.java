package com.example.demo.controller;


import com.example.demo.dto.CreateRoomRq;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.service.ChatRoomService;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
//@CrossOrigin("http://localhost:5173")
public class RoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/all_rooms")
    public List<ChatRoom> getAllByUser(){

        User user = new User();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUsername(username);
        java.util.List<ChatRoom> rooms = chatRoomService.getByUser(user);
        rooms.stream().forEach(item -> {
            item.setChats(List.of());});



        return rooms;
    }


    @PostMapping("/create")
    public ChatRoom createRoom(@RequestBody CreateRoomRq crq){

        return chatRoomService.create(crq);

    }




    @GetMapping("find/{name}")
    public ChatRoom findByName(@PathVariable("name") String name){
        return chatRoomService.findByName(name);
    }

}
