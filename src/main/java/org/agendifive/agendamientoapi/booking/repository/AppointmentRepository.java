package org.agendifive.agendamientoapi.booking.repository;

import org.agendifive.agendamientoapi.booking.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}