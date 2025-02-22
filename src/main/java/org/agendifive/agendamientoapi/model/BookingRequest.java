package org.agendifive.agendamientoapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class BookingRequest {

    private String nameClient;
    private String email;
    private String phone;
    private int specialistID;
    private int serviceID;
    private LocalDate dateAppointment;
    private LocalTime appointmentTime;


}
