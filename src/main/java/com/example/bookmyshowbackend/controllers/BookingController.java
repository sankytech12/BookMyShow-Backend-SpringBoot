package com.example.bookmyshowbackend.controllers;

import com.example.bookmyshowbackend.dtos.BookTicketRequestDTO;
import com.example.bookmyshowbackend.dtos.BookTicketResponseDTO;
import com.example.bookmyshowbackend.dtos.ResponseStatus;
import com.example.bookmyshowbackend.models.Booking;
import com.example.bookmyshowbackend.services.BookingService;

public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    BookTicketResponseDTO bookTicket(BookTicketRequestDTO request) {
        BookTicketResponseDTO response = new BookTicketResponseDTO();

        try {
            Booking booking=bookingService.bookTicket(request.getShowSeatIds(),request.getShowId(),request.getUserId());
            response.setBookingId(booking.getId());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setAmount(booking.getAmount());
        }catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
