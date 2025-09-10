package com.example.demo.controller;

import com.example.demo.dto.ChangesResp;
import com.example.demo.dto.DeleteResp;
import com.example.demo.model.Chat;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Random;

@Controller
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;


    @MessageMapping("/message")
    @SendTo("/topic/chat/")
    public Chat messageController(@Payload String payload) throws JsonProcessingException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(payload);
        Random random = new Random();
        Chat chat = new Chat();
        chat.setId(random.nextInt());
        chat.setMessage(node.path("content").asText());
        chat.setDate(new Date());
        System.out.println(chat.getDate());
        return chat;
    }


    @MessageMapping("/change")
    @SendTo("/topic/chat")
    public ChangesResp changeMessage(@Payload String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(message);
//        System.out.println(message);
        Chat chat = objectMapper.readValue(node.path("content").asText(),Chat.class);

//        boolean status = messageService.edit(chat);


            ChangesResp resp = new ChangesResp(chat);
            resp.setChanged(true);
            return resp;


//        ChangesResp resp = new ChangesResp();
//        resp.setStatus(status);
//        return resp;
    }



    @MessageMapping("/delete")
    public DeleteResp deleteMessage(@Payload String message) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode node = objectMapper.readTree(message);
//        Chat chat = new Chat();
//        chat.setId(node.asInt("id"));
        System.out.println(message);
        DeleteResp resp = new DeleteResp();
        resp.setStatus(true);
        return resp;
    }

    @SubscribeMapping("/sub")
    public String handleMessageWithoutResponse(String message) {
//        log.info("subscribed to /app: {}", message);
        System.out.println(message);
        return "server one-time message via the application";    }

}
