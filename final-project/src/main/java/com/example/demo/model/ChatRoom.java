package com.example.demo.model;

import com.example.demo.model.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("name")
    @Column(nullable = true)
    private String roomName;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "room_user")
    private List<User> members;

    @OneToMany(mappedBy = "room",fetch = FetchType.EAGER)
    private List<Chat> chats;


    public void sortChatsBytime(){
        this.chats.sort(((o1, o2) -> o1.getDate().compareTo(o2.getDate())));
    }

    public void setIsSent(Integer id){
        chats.stream().forEach((Chat item) -> {
            if (item.getId().equals(id)){
                item.setSent(true);
            }
            else item.setSent(false);
        });
    }
}
