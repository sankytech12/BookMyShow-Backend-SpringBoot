package com.example.bookmyshowbackend.repositories;

import com.example.bookmyshowbackend.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
