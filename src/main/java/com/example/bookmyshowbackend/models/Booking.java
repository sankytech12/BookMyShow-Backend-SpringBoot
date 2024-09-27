package com.example.bookmyshowbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{
    @OneToMany
    private List<ShowSeat> showSeats;
    private int amount;
    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;
    @ManyToOne
    private User bookedBy;
    private Date bookingDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
