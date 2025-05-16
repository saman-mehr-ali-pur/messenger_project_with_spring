package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.List;
import java.util.Optional;

@Service
public class UserSservice {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;


    public User saveUser(User user){

        user.setPassword(encoder.encode(user.getPassword()));
        return  userRepo.save(user);
    }

    public User findByUsername(User user){

        List<User> result = userRepo.findByUsername(user.getUsername());
        System.out.println("user service: "+result.size());
        if (result.isEmpty())
            return null;

        if (result.size()!=1){
            return null;
        }

        if (encoder.matches(user.getPassword(), result.get(0).getPassword())){
            System.out.println("user pass word matched");
            return result.get(0);
        }


        return null;
    }


    public Page<User> getAll(int page,int size){
        Page<User> result =  userRepo.findAll(PageRequest.of(page,size));
        return result;
    }

}
