package com.example.demo.service;


import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.repo.ChatRoomRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {


    @Autowired
    private ChatRoomRepo chatRoomRepo;
    @Autowired
    private UserRepo userRepo;

    public List<ChatRoom> getByUser(User user){

        List<User> users = userRepo.findByUsername(user.getUsername());
        if (users.isEmpty())
            return null;
        return chatRoomRepo.findAllByUserId(users.get(0).getId());

    }


    public ChatRoom findByName(String name){
        return chatRoomRepo.findByRoomName(name);
    }

}
