package org.agendifive.agendamientoapi.booking.controller;

import org.agendifive.agendamientoapi.booking.model.BookingRequest;
import org.agendifive.agendamientoapi.booking.model.BookingResponse;
import org.agendifive.agendamientoapi.booking.service.BookingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalendarController {



    @Autowired
    private BookingInterface bookingInterface;

    @PostMapping("/bookings")
    public BookingResponse saveBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingInterface.BookingSave(bookingRequest);
    }
}