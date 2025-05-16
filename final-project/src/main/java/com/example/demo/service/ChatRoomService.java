package com.example.demo.service;


import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.repo.ChatRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {


    @Autowired
    private ChatRoomRepo chatRoomRepo;

    public List<ChatRoom> getByUser(User user){

        return chatRoomRepo.findByUser(user.getId());

    }

}
