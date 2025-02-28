package org.agendifive.agendamientoapi.specialist.service;

import org.agendifive.agendamientoapi.specialist.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    // Obtener todos los especialistas activos (estado 'A')
    @Query("SELECT s FROM Specialist s WHERE s.status = 'A'")
    List<Specialist> findAllActiveSpecialists();

    // Obtener especialistas activos de un establecimiento específico
    @Query("SELECT s FROM Specialist s WHERE s.establishmentId = :establishmentId AND s.status = 'A'")
    List<Specialist> findActiveSpecialistsByEstablishment(Long establishmentId);
}