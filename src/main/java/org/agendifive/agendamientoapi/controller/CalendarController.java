package org.agendifive.agendamientoapi.controller;

import org.agendifive.agendamientoapi.model.BookingRequest;
import org.agendifive.agendamientoapi.model.BookingResponse;
import org.agendifive.agendamientoapi.service.BookingInterface;
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