package com.me.pe.currencyexchange.controller;

import com.me.pe.currencyexchange.model.api.security.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    /**
     * Authentication through a username/password login.
     * */
    @PostMapping("/user")
    public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        String token = generateJWTToken(username);
        User user = new User();
        user.setUser(username);
        user.setToken(token);
        return user;
    }

    private String generateJWTToken(String username) {
        String secretKey = "mySecretKey";

        //To authorize request to protected resources
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        //Delegate the JWT token generation
        JwtBuilder jwtBuilder = Jwts
                .builder()
                .setId("meJWT")
                .setSubject(username)
                .claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //10 Minutes of life
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                //Signature algorithm
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes());


        return "Bearer " + jwtBuilder.compact();
    }

}
