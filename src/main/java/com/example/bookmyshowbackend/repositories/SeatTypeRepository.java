package com.example.bookmyshowbackend.repositories;

import com.example.bookmyshowbackend.models.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
}
