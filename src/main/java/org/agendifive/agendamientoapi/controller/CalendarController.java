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
        return bookingInterface.bookingSave(bookingRequest);
    }

    @PostMapping("/reschedule")
    @Operation(
            summary = "Reagenda si tiene una cita existente",
            description = "Reagenda si tiene una cita existente verifica correo,fecha y servicio"
    )
    public BookingResponse reschedule(@RequestBody BookingRequest bookingRequest) {
        return bookingInterface.reschedule(bookingRequest);
    }


    @GetMapping("/getschedulebyspecialist/{specialistId}")
    @Operation(
            summary = "Obtiene agenda por especialista",
            description = "Retorna una lista de agendas por especialistas"
    )
    public BookingResponse getschedulebyspecialist(@PathVariable Integer specialistId) {
        return bookingInterface.getschedulebyspecialist(specialistId);
    }

    @GetMapping("/getschedulebyspecialistbydate/{specialistId}/{date}")
    @Operation(
            summary = "Obtiene agenda por especialista por dia",
            description = "Retorna una lista de agendas por especialistas"
    )
    public BookingResponse getschedulebyspecialistbydate(@PathVariable Integer specialistId,@PathVariable String date) {
        return bookingInterface.getschedulebyspecialistbydate(specialistId,date);
    }
}