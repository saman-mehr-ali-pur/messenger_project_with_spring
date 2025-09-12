package com.example.demo.controller;

import com.example.demo.dto.ChangesResp;
import com.example.demo.dto.DeleteResp;
import com.example.demo.model.Chat;
import com.example.demo.model.User;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/message")
    @SendTo("/topic/chat/")
    public void messageController(@Payload String payload) throws JsonProcessingException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(payload);
        Chat chat = new Chat();
        chat.setMessage(node.path("content").asText());
        chat.setDate(new Date() );
        Long reciverId = node.path("chatRoomid").asLong();
        String username = node.path("owner").asText();
        User  user = new User();
        user.setUsername(username);
        chat.setUser(user);
        System.out.println("chat content :"+payload);
        chat = messageService.saveChat(chat,reciverId);
//        chat.setUser(user);
        chat.getRoom().setChats(List.of());
        chat.setSent(true);
        messagingTemplate.convertAndSend("/topic/chat/"+username,chat);
    }


    @MessageMapping("/change")
    @SendTo("/topic/chat")
    public void changeMessage(@Payload String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(message);
        Chat chat = objectMapper.readValue(node.path("content").asText(),Chat.class);


        chat = messageService.edit(chat);
        ChangesResp resp = new ChangesResp(chat);
        resp.setChanged(true);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        messagingTemplate.convertAndSend("/topic/chat/"+username,resp);


    }



    @MessageMapping("/delete")
    public void deleteMessage(@Payload String message) throws JsonProcessingException {
        System.out.println("delete : "+message);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(message);
        String username = node.path("username").asText();
        Integer id = node.path("id").asInt();
        boolean result = messageService.delete(id,username);
        DeleteResp resp = new DeleteResp();
        resp.setStatus(result);
        resp.setId(id);
        messagingTemplate.convertAndSend("/topic/chat/"+username,resp);
    }

    @SubscribeMapping("/sub")
    public String handleMessageWithoutResponse(String message) {
//        log.info("subscribed to /app: {}", message);
        System.out.println(message);
        return "server one-time message via the application";    }

}
