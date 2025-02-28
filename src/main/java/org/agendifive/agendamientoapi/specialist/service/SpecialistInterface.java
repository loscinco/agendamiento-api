package org.agendifive.agendamientoapi.specialist.service;

import org.agendifive.agendamientoapi.specialist.model.ResponseSpecialist;

public interface SpecialistInterface {

    ResponseSpecialist getSpecialisActive(Long establishmentId);
}
