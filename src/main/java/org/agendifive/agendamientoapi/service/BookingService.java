package org.agendifive.agendamientoapi.service;


import org.agendifive.agendamientoapi.model.Appointment;
import org.agendifive.agendamientoapi.model.BookingRequest;
import org.agendifive.agendamientoapi.model.BookingResponse;
import org.agendifive.agendamientoapi.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.time.Duration;

@Service
public class BookingService implements BookingInterface {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public BookingResponse bookingSave(BookingRequest bookingRequest) {

        String error = validateBookingRequest(bookingRequest);
        if(error == null){
            try{
                BookingResponse response = validateAppointment(bookingRequest);
                if (response != null) {
                    return response; // Retorna inmediatamente si hay una cita existente
                }
                saveAppointment(bookingRequest);
                return responsesave(true);
            }catch (Exception e){
                e.printStackTrace();
                return responsesave(false);
            }
        }else{
            return responsesave(false);
        }

    }

    @Override
    public BookingResponse reschedule(BookingRequest bookingRequest) {
        String error = validateBookingRequest(bookingRequest);
        if(error == null){
            try {
                Appointment appointment = appointmentRepository.appointmentOnTheSameDay(bookingRequest.getEmail(),bookingRequest.getDateAppointment(),bookingRequest.getServiceID());
                if(appointment != null){
                    appointment.setStatus("I");
                    appointmentRepository.save(appointment);
                }
                saveAppointment(bookingRequest);
                return responsesave(true);
            }catch (Exception e){
                return responsesave(false);
            }

        }
            return responsesave(false);

    }

    @Override
    public BookingResponse getschedulebyspecialist(Integer specialistId) {
        LocalDate today = LocalDate.now(ZoneId.of("America/Bogota"));
        BookingResponse response = new BookingResponse();
        List<Appointment> appointments = new ArrayList<>();
        appointments = appointmentRepository.findAppointmentsBySpecialist(specialistId,today);
        if(appointments.size() > 0){
            response.setStatus("OK");
            response.setBusinessMessage("Agenda encontrada correctamente");
            response.setData(appointments);
            return response;
        }
        response.setStatus("NOOK");
        response.setBusinessMessage("No existe agenda para el escecialista seleccionado");

        return response;
    }

    @Override
    public BookingResponse getschedulebyspecialistbydate(Integer specialistId, String date) {
        LocalDate appointmentDate = null;
        BookingResponse bookingResponse = new BookingResponse();

        List<Appointment> appointmentList = new ArrayList<>();
        try {
            appointmentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            appointmentList = appointmentRepository.findAppointmentsBySpecialistAndDate(specialistId,appointmentDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            System.out.println("Fecha inválida: " + e.getMessage());
            bookingResponse.setStatus("NOOK");
            bookingResponse.setBusinessMessage("la fecha debe estar en formato yyyy-MM-dd");
            return bookingResponse;
        }

        if(appointmentList.size() > 0){
            bookingResponse.setData(appointmentList);
        }
        bookingResponse.setStatus("OK");
        bookingResponse.setBusinessMessage("agenda obtenida correctamente");

        return bookingResponse;
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

        return null;
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

    private BookingResponse validateAppointment(BookingRequest request){
        //si tiene cita para el mismo dia, correo y servicio no se guarda
        BookingResponse response = new BookingResponse();
        Appointment appointment = appointmentRepository.appointmentOnTheSameDay(request.getEmail(),request.getDateAppointment(),request.getServiceID());

        if(appointment != null){
            response.setStatus("NOOK");
            response.setBusinessMessage("Usted tiene una cita para el serivico y fecha seleccionada");
            return response;
        }
        return null;
    }

    private void saveAppointment(BookingRequest bookingRequest){
        ZonedDateTime colombiaTime = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        ZonedDateTime colombiaTimeMinus5 = colombiaTime.minus(Duration.ofHours(5));//restamos 5 horas
        Appointment appointment = new Appointment();
        appointment.setStatus("A");
        appointment.setAppointmentDate(bookingRequest.getDateAppointment());
        appointment.setFullName(bookingRequest.getNameClient());
        appointment.setEmail(bookingRequest.getEmail());
        appointment.setPhone(bookingRequest.getPhone());
        appointment.setSpecialist(bookingRequest.getSpecialistID());
        appointment.setService(bookingRequest.getServiceID());
        appointment.setAppointmentTime(bookingRequest.getAppointmentTime());// pendiente validar
        appointment.setCreatedAt(Date.from(colombiaTimeMinus5.toInstant()));
        appointment.setAppointmentTimeFinish(bookingRequest.getAppointmentTime().plusMinutes(bookingRequest.getServiceDuration()));
        appointmentRepository.save(appointment);
    }
}
