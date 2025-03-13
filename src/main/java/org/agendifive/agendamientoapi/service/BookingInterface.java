package org.agendifive.agendamientoapi.service;

import org.agendifive.agendamientoapi.model.BookingRequest;
import org.agendifive.agendamientoapi.model.BookingResponse;

public interface BookingInterface {

    BookingResponse bookingSave(BookingRequest bookingRequest);
    BookingResponse reschedule(BookingRequest bookingRequest);
    BookingResponse getschedulebyspecialist(Integer specialistId);
    BookingResponse getschedulebyspecialistbydate(Integer specialistId,String Date);
}
