package org.agendifive.agendamientoapi.booking.model;

public class BookingResponse {
    private static final long serialVersionUID = -6068974569530002278L;

    // Instancia de ReturnStatus que implementa ReturnStatusInterface
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
}
