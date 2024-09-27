package com.example.bookmyshowbackend.services;

import com.example.bookmyshowbackend.models.*;
import com.example.bookmyshowbackend.repositories.BookingRepository;
import com.example.bookmyshowbackend.repositories.ShowRepository;
import com.example.bookmyshowbackend.repositories.ShowSeatRepository;
import com.example.bookmyshowbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;

    @Autowired
    public BookingService(UserRepository userRepository,
                          BookingRepository bookingRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookTicket(List<Integer> showSeatIds, int showId, int userId){


        //1. Find user using userId
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();
        //2. Get show using showId
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException("Show not found");
        }
        Show show = showOptional.get();
        //-----------------START TRANSACTION------------------
        //3. Get ShowSeats via showSeatIds
        List<ShowSeat> showSeats=showSeatRepository.findAllById(showSeatIds);
        //4. check if all the seats are available
        for(ShowSeat showSeat:showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                //6. If no then return a error message
                throw new RuntimeException("Show seat status is not AVAILABLE");
            }
        }
        //5. If yes then -> Block the seats, save the seats in the db,
        for(ShowSeat showSeat:showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
        }
        showSeatRepository.saveAll(showSeats);
        // ------- STOP TRANSACTION ------


        // Calculate the price
        int totalAmount = 0;
        for (ShowSeat showSeat : showSeats) {
            SeatType seatType = showSeat.getSeat().getSeatType();  // Get the seat type of the current seat

            // Find the corresponding ShowSeatType in the show
            Optional<ShowSeatType> showSeatTypeOptional = show.getShowSeatTypes().stream()
                    .filter(showSeatType -> showSeatType.getSeatType().equals(seatType))
                    .findFirst();

            if (showSeatTypeOptional.isPresent()) {
                int price = showSeatTypeOptional.get().getPrice();  // Get the price for the seat type
                totalAmount += price;  // Add the price to the total amount
            } else {
                throw new RuntimeException("Price not found for seat type: " + seatType.getValue());
            }
        }
        // Future Score: To create a separate PriceCalculatorService


        // Create booking obj and save and return it
        Booking booking = new Booking();
        booking.setBookedBy(user);
        booking.setBookingDate(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(totalAmount);
        booking.setPayments(new ArrayList<>());
        booking.setShowSeats(showSeats);

        return bookingRepository.save(booking);
    }
}
