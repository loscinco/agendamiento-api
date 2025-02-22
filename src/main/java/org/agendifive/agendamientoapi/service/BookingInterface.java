package org.agendifive.agendamientoapi.service;

import org.agendifive.agendamientoapi.model.BookingRequest;
import org.agendifive.agendamientoapi.model.BookingResponse;

public interface BookingInterface {

    BookingResponse BookingSave(BookingRequest bookingRequest);
}
