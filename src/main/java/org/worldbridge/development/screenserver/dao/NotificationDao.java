package org.worldbridge.development.screenserver.dao;

import org.worldbridge.development.screenserver.domain.Notification;

/**
 * Created by hugo on 11-08-16.
 */
public interface NotificationDao {
    Notification getNotificationForDevice(String deviceId);
}
