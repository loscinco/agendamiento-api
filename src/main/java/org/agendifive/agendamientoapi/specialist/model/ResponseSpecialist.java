package org.agendifive.agendamientoapi.specialist.model;

import org.agendifive.agendamientoapi.specialist.model.SpecialistDTO;

import java.util.List;

public class ResponseSpecialist {
    private boolean success;
    private String message;
    private List<SpecialistDTO> data;

    // Constructor completo
    public ResponseSpecialist(boolean success, String message, List<SpecialistDTO> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Constructor vacío
    public ResponseSpecialist() {
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SpecialistDTO> getData() {
        return data;
    }

    public void setData(List<SpecialistDTO> data) {
        this.data = data;
    }
}
