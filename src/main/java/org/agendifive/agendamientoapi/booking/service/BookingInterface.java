package org.agendifive.agendamientoapi.booking.service;

import org.agendifive.agendamientoapi.booking.model.BookingRequest;
import org.agendifive.agendamientoapi.booking.model.BookingResponse;

public interface BookingInterface {

    BookingResponse BookingSave(BookingRequest bookingRequest);
}
