package org.agendifive.agendamientoapi.repository;

import org.agendifive.agendamientoapi.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT * FROM appointment " +
            "WHERE specialist = :specialist AND appointment_date >= :today " +
            "ORDER BY appointment_date ASC, appointment_time ASC", nativeQuery = true)
    List<Appointment> findAppointmentsBySpecialist(
            @Param("specialist") Integer specialist, @Param("today") LocalDate today);

    @Query("SELECT a FROM Appointment a WHERE a.specialist = :specialistId AND a.appointmentDate = :appointmentDate and a.status='A' ")
    List<Appointment> findAppointmentsBySpecialistAndDate(
            @Param("specialistId") Integer specialistId,
            @Param("appointmentDate") LocalDate appointmentDate);

    @Query("SELECT a FROM Appointment a WHERE a.email = :email AND a.appointmentDate = :appointmentDate and a.status='A' and a.service= :serviceId ")
    Appointment appointmentOnTheSameDay(
            @Param("email") String email,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("serviceId") Integer serviceId);


}