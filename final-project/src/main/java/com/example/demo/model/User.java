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

@Data
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
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(name = "Role",nullable = false)
    private String role;
    @Column(length = 100)
    String description;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean Enable=false;
//    @ManyToMany(mappedBy = "members",fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<ChatRoom> ChatRoom;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "profiles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "path", nullable = true)
        private List<String> profilePaths;

}

