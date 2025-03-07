package org.agendifive.agendamientoapi.model;

import java.util.List;

public class BookingResponse {
    private static final long serialVersionUID = -6068974569530002278L;

    // Instancia de ReturnStatus que implementa ReturnStatusInterface
    private List<Appointment> data;
    private ReturnStatusInterface returnStatus = new ReturnStatus();

    // Constructor que permite inicializar con estado y mensaje


    // Getter
    public ReturnStatusInterface getReturnStatus() {
        return returnStatus;
    }

    // Setter
    public void setReturnStatus(ReturnStatusInterface returnStatus) {
        this.returnStatus = returnStatus;
    }

    // Métodos auxiliares para simplificar la creación de respuestas
    public void setStatus(String status) {
        this.returnStatus.setStatus(status);
    }

    public void setBusinessMessage(String message) {
        this.returnStatus.setBusinessMessage(message);
    }

    public List<Appointment> getData() {
        return data;
    }

    public void setData(List<Appointment> data) {
        this.data = data;
    }
}
