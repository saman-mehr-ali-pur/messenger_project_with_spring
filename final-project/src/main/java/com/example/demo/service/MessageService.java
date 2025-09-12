package com.example.demo.service;

import com.example.demo.model.Chat;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.model.enums.RoomType;
import com.example.demo.repo.ChatRepo;
import com.example.demo.repo.ChatRoomRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private ChatRepo chatRepo;
    @Autowired private ChatRoomRepo chatRoomRepo;

    @Autowired private UserRepo userRepo;
    public Chat saveChat(Chat chat,Long reciverId){

        System.out.println("username : "+chat.getUser().getUsername());
        User user = userRepo.findByUsername(chat.getUser().getUsername()).get(0);
        User reciverUser = userRepo.findById(reciverId).orElse(null);
        ChatRoom chatRoom = chatRoomRepo.findByUserId( user.getId(),reciverId, RoomType.PRIVATE);
//        System.out.println(String.format("chatroom : %d \n owner: %s \n reciver : %d",chatRoom.getId(),user.getUsername(),reciverUser.getUsername()));
        if (chatRoom==null){
            chatRoom= new ChatRoom();
            chatRoom.setType(RoomType.PRIVATE);
            User user1 = new User();
            User user2 = new User();
            chatRoom.setMembers(List.of(user,reciverUser));
            chatRoom.setRoomName("room: "+user1.getId()+" "+user2.getId());
            chatRoomRepo.save(chatRoom);
        }
        chat.setRoom(chatRoom);
        chat.setUser(user);

        return chatRepo.save(chat);

    }

    public Chat edit(Chat chat){
        Optional<Chat> result = chatRepo.findById(chat.getId());
        if(result.isEmpty())
            return null;

        result.get().setMessage(chat.getMessage());
        return chatRepo.save(result.get());

    }



    public List<Chat> getAllByRoom(ChatRoom chatRoom, Pageable page){

        return chatRepo.findByRoom(chatRoom, page);

    }



}
