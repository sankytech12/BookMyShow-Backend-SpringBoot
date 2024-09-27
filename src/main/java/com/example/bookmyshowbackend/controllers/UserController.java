package com.example.bookmyshowbackend.controllers;

import com.example.bookmyshowbackend.dtos.ResponseStatus;
import com.example.bookmyshowbackend.dtos.SignUpRequestDTO;
import com.example.bookmyshowbackend.dtos.SignUpResponseDTO;
import com.example.bookmyshowbackend.models.User;
import com.example.bookmyshowbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    public SignUpResponseDTO signUp(SignUpRequestDTO request) {
        SignUpResponseDTO response = new SignUpResponseDTO();

        try {
            User user = userService.signUp(request.getEmail(), request.getPassword());
            response.setUserId(user.getId());
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("User successfully registered!");

        }catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
