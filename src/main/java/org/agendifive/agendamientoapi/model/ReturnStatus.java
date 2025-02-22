package org.agendifive.agendamientoapi.model;

import java.io.Serializable;

public class ReturnStatus implements Serializable, ReturnStatusInterface {
    private static final long serialVersionUID = 7317593807498584602L;
    private String businessMessage;
    private String status = "OK";

    public String getBusinessMessage() {
        return businessMessage;
    }

    public void setBusinessMessage(String businessMessage) {
        this.businessMessage = businessMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
