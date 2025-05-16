package com.example.demo.configuration.securityConfig;

import com.example.demo.model.User;
import com.example.demo.model.UserDetailsImp;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
public class UserDetailServiceConfig {

    @Autowired
    private UserRepo userRepo;

    @Bean
    public UserDetailsService getUserDetailService(){



        return (String username) ->{
            List<User> users=userRepo.findByUsername(username);

            if (users.size()==1){
                UserDetailsImp userDetails = new UserDetailsImp();
                userDetails.setUsername(users.get(0).getUsername());
                userDetails.setPassword(users.get(0).getPassword());
                userDetails.setAuthorities(List.of(new SimpleGrantedAuthority(users.get(0).getRole())));
                return userDetails;
            }

            return null;
        };
    }


}
