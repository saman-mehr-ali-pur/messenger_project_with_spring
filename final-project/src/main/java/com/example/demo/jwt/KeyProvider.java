package com.example.demo.jwt;


import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

public class KeyProvider {

    public static SecretKey key;

    public KeyProvider(){

        this.key = Jwts.SIG.HS512.key().build();

    }

}
