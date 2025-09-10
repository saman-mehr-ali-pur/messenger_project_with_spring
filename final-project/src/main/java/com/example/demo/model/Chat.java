package com.example.demo.model;

import com.example.demo.model.enums.ChatType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Entity
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ChatType type;
    @Column(name = "send_time")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss a",timezone = "Asia/Tehran")
    private Date date;
    @Column
    private String mediaPath;


    public Chat(Chat chat) {
        this.id = chat.id;
        this.message = chat.message;
        this.user = chat.user;
        this.type = chat.type;
        this.date = chat.date;
        this.mediaPath = chat.mediaPath;
        this.room = chat.room;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room")
    private ChatRoom room;
}
