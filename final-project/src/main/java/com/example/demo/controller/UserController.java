package com.example.demo.controller;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserSservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSservice userSservices;

//    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public User saveUser(@RequestBody User user){
        user.setEnable(false);
        user.setRole("USER");


        return  userSservices.saveUser(user);
    }

    @PostMapping("/get")
    public User getUser(@RequestBody User user){

        return  userSservices.findByUsername(user);

    }

    @GetMapping("/all/")
    public List<User> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size){

        return  userSservices.
                getAll(page,size).
                    stream().
                        toList();

    }

    @GetMapping("/check_username/{username}")
    public ResponseEntity checkUsername(@PathVariable("username") String username){

        if (! userSservices.isExist(username)){
            return ResponseEntity.status(404).body(false);
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(true);
    }

//
//    @GetMapping("/check_username/{username}")
//    public boolean checkUsername(@PathVariable("username") String username){
//
//        if (! userSservices.isExist(username)){
//            throw  new NotFoundException("user not found");
//        }
//
//        return true;
//    }
//

}
