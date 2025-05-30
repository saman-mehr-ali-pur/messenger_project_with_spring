package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fName;
    @Column(nullable = false)
    private String lName;
    @Column(name = "Role",nullable = false)
    private String role;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean Enable=false;
    @ManyToMany(mappedBy = "members",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChatRoom> ChatRoom;

    @ElementCollection(targetClass = String.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "profiles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "path", nullable = true)
    private List<String> profilePaths;


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEnable(Boolean enable) {
        Enable = enable;
    }

    public void setChatRoom(List<com.example.demo.model.ChatRoom> chatRoom) {
        ChatRoom = chatRoom;
    }

    public void setProfilePaths(List<String> profilePaths) {
        this.profilePaths = profilePaths;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public Boolean getEnable() {
        return Enable;
    }

    public List<com.example.demo.model.ChatRoom> getChatRoom() {
        return ChatRoom;
    }

    public List<String> getProfilePaths() {
        return profilePaths;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

