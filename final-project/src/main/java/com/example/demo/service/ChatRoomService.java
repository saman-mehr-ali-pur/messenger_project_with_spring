package com.example.demo.service;


import com.example.demo.dto.CreateRoomRq;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.model.enums.RoomType;
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

    public ChatRoom getByTwoid(Long id1,Long id2){
        return chatRoomRepo.findByUserId(id1,id2, RoomType.PRIVATE).orElse(null);
    }

    public ChatRoom create(CreateRoomRq crq) {

        ChatRoom chatRoom = new ChatRoom();

        User user1 = userRepo.findByUsername(crq.getUser1().getUsername()).get(0);
        User user2 = userRepo.findById(crq.getUser2().getId()).orElse(null);

        if (user1!=null && user2!=null){

            chatRoom.setMembers(List.of(user1,user2));
            chatRoom.setRoomName(user1.getUsername()+" "+user2.getUsername());
            return  chatRoomRepo.save(chatRoom);
        }

        return null;


    }
}
