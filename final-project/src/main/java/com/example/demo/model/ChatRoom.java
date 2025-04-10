package com.example.demo.model;

import com.example.demo.model.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String roomName;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "room_user")
    private List<User> members;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    private List<Chat> chats;
}
