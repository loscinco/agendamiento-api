package org.agendifive.agendamientoapi.service;

import org.agendifive.agendamientoapi.model.NotificationRequest;

public interface NotificationInterface {

    Boolean sendNotification(NotificationRequest notificationRequest);
}
