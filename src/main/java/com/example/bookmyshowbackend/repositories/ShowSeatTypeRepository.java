package com.example.bookmyshowbackend.repositories;

import com.example.bookmyshowbackend.models.Show;
import com.example.bookmyshowbackend.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Integer> {
    List<ShowSeatType> findAllByShow(Show show);
}
