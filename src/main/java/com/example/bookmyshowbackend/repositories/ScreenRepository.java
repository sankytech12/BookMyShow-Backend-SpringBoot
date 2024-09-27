package com.example.bookmyshowbackend.repositories;

import com.example.bookmyshowbackend.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Integer> {
}
