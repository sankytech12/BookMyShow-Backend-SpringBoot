package com.example.bookmyshowbackend.services;

import com.example.bookmyshowbackend.models.Booking;
import com.example.bookmyshowbackend.models.User;
import com.example.bookmyshowbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User signUp(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new RuntimeException("User already exists");
        }
        User result = new User();
        result.setEmail(email);
        result.setPassword(password);
        result.setBookingList(new ArrayList<Booking>());
        result.setFirstName("Temp");
        result.setLastName("Temp");
        result.setUsername(email);
        return userRepository.save(result);
    }

}
