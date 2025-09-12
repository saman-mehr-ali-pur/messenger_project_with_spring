package com.example.demo.controller;


import com.example.demo.dto.AuthDto;
import com.example.demo.jwt.JwtService;
import com.example.demo.model.User;
import com.example.demo.model.UserDetailsImp;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userSservice;
    @Autowired
    private JwtService jwtService;


//    @CrossOrigin(origins = "*")
    @PostMapping
//    @CrossOrigin("localhost:5173")
    public ResponseEntity<AuthDto> doAuth(@RequestBody User user){

//        System.out.println(user.getUsername()+" "+user.getPassword());
        User user1= userSservice.findByUsername(user);
        System.out.println(user1==null);
        if (user1==null)
            return null;


        Map<String,Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        UserDetailsImp userDetails = new UserDetailsImp();
        userDetails.setUsername(user.getUsername());
        String token = jwtService.generateToken(claims,userDetails);
        AuthDto authDto = new AuthDto();
        authDto.setUsername(user1.getUsername());
        authDto.setToken(token);

        ResponseCookie cookie = ResponseCookie.from("Authorization",token).httpOnly(true).path("/").
                maxAge(24*60*60).sameSite("Strict").build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,cookie.toString());
        return ResponseEntity.ok().headers(headers).body(authDto);
    }
}
