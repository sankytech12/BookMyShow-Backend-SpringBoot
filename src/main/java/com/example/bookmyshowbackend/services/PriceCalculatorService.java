package com.example.bookmyshowbackend.services;

import com.example.bookmyshowbackend.models.Show;
import com.example.bookmyshowbackend.models.ShowSeat;
import com.example.bookmyshowbackend.models.ShowSeatType;
import com.example.bookmyshowbackend.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculatorService {
    @Autowired
    private ShowSeatTypeRepository showSeatTypeRepository;

    public PriceCalculatorService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(Show show, List<ShowSeat> showSeats) {
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository
                .findAllByShow(show);

        int amount = 0;
        for(ShowSeat showSeat: showSeats) { // ShowSeats selected by the user
            for(ShowSeatType showSeatType: showSeatTypes) { // All showseattypes that exist in the show
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                }
            }
        }

        return amount;
    }
}
