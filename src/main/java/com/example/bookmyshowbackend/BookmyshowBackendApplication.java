package com.example.bookmyshowbackend;

import com.example.bookmyshowbackend.controllers.UserController;
import com.example.bookmyshowbackend.dtos.ResponseStatus;
import com.example.bookmyshowbackend.dtos.SignUpRequestDTO;
import com.example.bookmyshowbackend.dtos.SignUpResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookmyshowBackendApplication implements CommandLineRunner {
    @Autowired
    UserController userController;

    @Override
    public void run(String... args) throws Exception {
        SignUpRequestDTO req = new SignUpRequestDTO();
        req.setEmail("david@gmail.com");
        req.setPassword("password");
        SignUpResponseDTO resp = userController.signUp(req);
        System.out.println(resp.getMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(BookmyshowBackendApplication.class, args);
    }

}
