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


}