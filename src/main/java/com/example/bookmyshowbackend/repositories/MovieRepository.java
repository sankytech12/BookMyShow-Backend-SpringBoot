package com.example.bookmyshowbackend.repositories;

import com.example.bookmyshowbackend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
