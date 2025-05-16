package com.example.demo.controller;


import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.service.ChatRoomService;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin("http://localhost:5173")
public class RoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/all_rooms")
    public List<ChatRoom> getAllByUser(@RequestBody User user){

        return chatRoomService.getByUser(user);
    }



}
