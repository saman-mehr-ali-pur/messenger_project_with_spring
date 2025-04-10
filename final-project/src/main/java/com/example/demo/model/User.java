package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity()
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fName;
    @Column(nullable = false)
    private String lName;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean Enable;
    @ManyToMany(mappedBy = "members",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> ChatRoom;

    @ElementCollection(targetClass = String.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "profiles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "path", nullable = false)
    private List<String> profilePaths;




}
