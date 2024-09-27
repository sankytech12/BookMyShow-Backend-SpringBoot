package com.example.bookmyshowbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;
    @OneToMany
    private List<Seat> seats;
    @ManyToOne
    private Theatre theatre;
    @Enumerated(value = EnumType.ORDINAL)
    @ElementCollection
    private List<ScreenFeature> screenFeatures;
}
