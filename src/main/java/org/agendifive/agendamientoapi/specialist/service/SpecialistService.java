package org.agendifive.agendamientoapi.specialist.service;

import org.agendifive.agendamientoapi.specialist.model.ResponseSpecialist;
import org.agendifive.agendamientoapi.specialist.model.Specialist;
import org.agendifive.agendamientoapi.specialist.model.SpecialistDTO;
import org.agendifive.agendamientoapi.specialist.model.SpecialistSchedule;
import org.agendifive.agendamientoapi.specialist.service.SpecialistInterface;
import org.agendifive.agendamientoapi.specialist.service.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;
@Service
public class SpecialistService implements SpecialistInterface {

    @Autowired
    private SpecialistRepository specialistRepository;
    @Override
    public ResponseSpecialist getSpecialisActive(Long establishmentId) {
        List<Specialist> specialistList = specialistRepository.findActiveSpecialistsByEstablishment(establishmentId);
        ResponseSpecialist responseSpecialist = new ResponseSpecialist(false,"No se encuentran especialistas para el establecimiento",null);
        if(specialistList.size()>0){
            responseSpecialist.setSuccess(true);
            responseSpecialist.setMessage("Especialistas obtenidos correctamente");
            responseSpecialist.setData(convertToDtoList(specialistList));
        }

        return responseSpecialist;
    }


    private List<SpecialistDTO> convertToDtoList(List<Specialist> specialists) {
        return specialists.stream()
                .map(specialist -> {
                    SpecialistDTO dto = new SpecialistDTO();
                    dto.setFirstName(specialist.getFirstName());
                    dto.setLastName(specialist.getLastName());
                    dto.setEmail(specialist.getEmail());
                    dto.setPhone(specialist.getPhone());

                    // Obtener el primer SpecialistSchedule (si existe)
                    if (specialist.getSpecialistSchedules() != null && !specialist.getSpecialistSchedules().isEmpty()) {
                        SpecialistSchedule schedule = specialist.getSpecialistSchedules().get(0);
                        dto.setDayOfWeek(schedule.getDayOfWeek());
                        dto.setStartTime(schedule.getStartTime());
                        dto.setEndTime(schedule.getEndTime());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
