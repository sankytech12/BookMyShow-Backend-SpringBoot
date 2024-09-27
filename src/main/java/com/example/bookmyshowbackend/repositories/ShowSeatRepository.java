package com.example.bookmyshowbackend.repositories;

import com.example.bookmyshowbackend.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {
    List<ShowSeat> findAllById(Iterable<Integer> showSeatIds);
}
