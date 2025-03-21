package org.agendifive.agendamientoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.agendifive.agendamientoapi.model.NotificationRequest;
import org.agendifive.agendamientoapi.service.NotificationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationContorller {

    @Autowired
    private NotificationInterface notificationInterface;
    @PostMapping("/sendNotification")
    @Operation(
            summary = "Genera notificacion",
            description = "realiza notificaciones mediante correo electronico"
    )
    public Boolean sendNotification(@RequestBody NotificationRequest notificationRequest) {
        return notificationInterface.sendNotification(notificationRequest);
    }
}
