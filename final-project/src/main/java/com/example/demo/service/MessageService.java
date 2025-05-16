package com.example.demo.service;

import com.example.demo.model.Chat;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.repo.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private ChatRepo chatRepo;

    public Chat saveChat(Chat chat){
        return chatRepo.save(chat);

    }

    public boolean edit(Chat chat){
        Optional<Chat> result = chatRepo.findById(chat.getId());
        if(result.isEmpty())
            return false;

        result.get().setMessage(chat.getMessage());
        chatRepo.save(result.get());
        return true;

    }



    public List<Chat> getAllByRoom(ChatRoom chatRoom, Pageable page){

        return chatRepo.findByRoom(chatRoom, page);

    }


}
