package org.agendifive.agendamientoapi.service;


import org.agendifive.agendamientoapi.model.Appointment;
import org.agendifive.agendamientoapi.model.BookingRequest;
import org.agendifive.agendamientoapi.model.BookingResponse;
import org.agendifive.agendamientoapi.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class BookingService implements BookingInterface {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public BookingResponse BookingSave(BookingRequest bookingRequest) {

        String error = validateBookingRequest(bookingRequest);
        if(error != null){
            Appointment appointment = new Appointment();
            appointment.setStatus("A");
            appointment.setAppointmentDate(bookingRequest.getDateAppointment());
            appointment.setFullName(bookingRequest.getNameClient());
            appointment.setEmail(bookingRequest.getEmail());
            appointment.setPhone(bookingRequest.getPhone());
            appointment.setSpecialist(bookingRequest.getSpecialistID());
            appointment.setService(bookingRequest.getServiceID());
            appointment.setAppointmentTime(bookingRequest.getAppointmentTime());// pendiente validar
            appointmentRepository.save(appointment);
            return responsesave(true);
        }else{
            return responsesave(false);
        }

    }

    private String validateBookingRequest(BookingRequest request) {
        Map<String, Function<BookingRequest, String>> validations = new HashMap<>();

        validations.put("nameClient", r -> (r.getNameClient() == null || r.getNameClient().isEmpty()) ? "El nombre del cliente es obligatorio." : null);
        validations.put("email", r -> (r.getEmail() == null || r.getEmail().isEmpty()) ? "El correo electrónico es obligatorio." :
                !isValidEmail(r.getEmail()) ? "El correo electrónico no es válido." : null);
        validations.put("phone", r -> (r.getPhone() == null || r.getPhone().isEmpty()) ? "El teléfono es obligatorio." :
                !isValidPhone(r.getPhone()) ? "El teléfono no es válido." : null);
        validations.put("specialistID", r -> (r.getSpecialistID() <= 0) ? "El ID del especialista debe ser mayor que cero." : null);
        validations.put("serviceID", r -> (r.getServiceID() <= 0) ? "El ID del servicio debe ser mayor que cero." : null);


        validations.put("dateAppointment", r -> (r.getDateAppointment() == null) ? "La fecha de la cita es obligatoria." :
                r.getDateAppointment().isBefore(LocalDate.now()) ? "La fecha de la cita no puede ser en el pasado." : null);



        for (Map.Entry<String, Function<BookingRequest, String>> entry : validations.entrySet()) {
            String errorMessage = entry.getValue().apply(request);
            if (errorMessage != null) {
                return errorMessage;
            }
        }

        return "Validación exitosa.";
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private boolean isValidPhone(String phone) {
        String phoneRegex = "^\\d{10}$";
        return phone.matches(phoneRegex);
    }

    private BookingResponse responsesave(Boolean save){
        BookingResponse bookingResponse = new BookingResponse();
        if(save){
            bookingResponse.setBusinessMessage("La reserva fue creada correctamente");
            bookingResponse.setStatus("OK");
        }else{
            bookingResponse.setBusinessMessage("Existio un problema al crear la reserva");
            bookingResponse.setStatus("OK");
        }

        return bookingResponse;
    }
}
