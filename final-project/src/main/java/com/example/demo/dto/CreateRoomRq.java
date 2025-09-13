package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class CreateRoomRq {

    private User user1;
    private User user2;

}
