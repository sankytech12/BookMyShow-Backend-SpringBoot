package com.example.bookmyshowbackend.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    private String title;
    private String director;
    private String year;
    private String genre;
    @ElementCollection
    private List<String> actors;
    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<Language> languages;
}
