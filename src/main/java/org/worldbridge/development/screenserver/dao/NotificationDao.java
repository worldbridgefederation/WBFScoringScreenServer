package org.worldbridge.development.screenserver.dao;

import org.worldbridge.development.screenserver.domain.Notification;
import org.worldbridge.development.screenserver.domain.NotificationDetails;

import java.util.List;

/**
 * Created by hugo on 11-08-16.
 */
public interface NotificationDao {
    Notification getNotificationForDevice(String deviceId);

    List<NotificationDetails> loadNotifications();

    void addNotification(NotificationDetails notificationDetails) throws DaoException;
}
