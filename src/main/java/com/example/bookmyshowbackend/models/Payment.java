package com.example.bookmyshowbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    private int amount;
    private String refNumber;
    private Date paymentDate;
    @Enumerated(value = EnumType.ORDINAL)
    private PaymentStatus status;
    @Enumerated(value = EnumType.ORDINAL)
    private PaymentGateway paymentGateway;
    @Enumerated(value = EnumType.ORDINAL)
    private PaymentMode paymentMode;
    @ManyToOne
    private Booking booking;
}
