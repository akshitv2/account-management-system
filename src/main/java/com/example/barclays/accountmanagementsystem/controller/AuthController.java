package com.example.barclays.accountmanagementsystem.controller;
import com.example.barclays.accountmanagementsystem.dto.ApiResponse;
import com.example.barclays.accountmanagementsystem.entity.User;
import com.example.barclays.accountmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user){
        return userService.loginUser(user);
    }

    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody User user){
        return userService.save(user);
    }
}
