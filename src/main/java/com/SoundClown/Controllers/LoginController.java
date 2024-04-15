package com.SoundClown.Controllers;

import com.SoundClown.User.*;
import com.SoundClown.Track.*;
import com.SoundClown.Playlist.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;


import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@SessionAttributes({"username", "password"})
public class  LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    /*
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody String json,
                                 HttpServletResponse response) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        String username = inputMap.get("user_name");
        String password = inputMap.get("password");

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            ResponseCookie cookie = ResponseCookie.from("username", username)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .domain("localhost")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok().build();
        } else {
            throw new UsernameNotFoundException("invalid user request");
        }
    }

     */


    /*
    @PostMapping("/login")
    public ResponseEntity login (@CookieValue(name = "username", defaultValue = "default-username") String cookieId,
                                 @RequestBody String json) throws JsonProcessingException {

        if (cookieId != null) {
            System.out.println("value of the cookie with name user-id is " + cookieId);
            return ResponseEntity.ok().build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        String username = inputMap.get("user_name");
        String password = inputMap.get("password");

        System.out.println(username);
        System.out.println(password);


        ResponseCookie resCookie = this.authenticationService.loginUser(username, password);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).build();
    }

     */


}
