package org.agendifive.agendamientoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/getschedulebyspecialist/{specialistId}")
    @Operation(
            summary = "Obtiene agenda por especialista",
            description = "Retorna una lista de agendas por especialistas"
    )
    public BookingResponse getschedulebyspecialist(@PathVariable Integer specialistId) {
        return bookingInterface.getschedulebyspecialist(specialistId);
    }
}