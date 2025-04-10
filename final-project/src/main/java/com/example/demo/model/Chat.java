package com.example.demo.model;

import com.example.demo.model.enums.ChatType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity

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
    private Date data;
    @Column
    private String mediaPath;

   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room")
    private ChatRoom room;


}
