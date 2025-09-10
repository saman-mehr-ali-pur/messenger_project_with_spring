package com.example.demo.intializer;


import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandlineRunnerImp implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {

       List<User> result = userRepo.findByUsername("admin");
       if (!result.isEmpty())
           return;

       else {
           User user = new User();
           user.setUsername("admin");
           user.setPassword("1234567");
           user.setPassword("admin init");
           user.setRole("ADMIN");
           user.setFName("admin1");
           user.setLName("admin1");
           user.setEnable(true);
           user.setEmail("admin@admin.com");

           userRepo.save(user);
       }
    }
}
