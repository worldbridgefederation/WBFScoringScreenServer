package org.worldbridge.development.screenserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.worldbridge.development.screenserver.domain.Notification;
import org.worldbridge.development.screenserver.persistance.NotificationEntity;
import org.worldbridge.development.screenserver.persistance.NotificationRepository;
import org.worldbridge.development.screenserver.persistance.ScreenEntity;
import org.worldbridge.development.screenserver.persistance.ScreenRepository;

import java.util.Collection;

@Component
public class NotificationJPADaoImpl implements NotificationDao {
    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification getNotificationForDevice(String deviceId) {
        ScreenEntity screenEntity = screenRepository.findOne(deviceId);
        if (screenEntity == null) {
            return null;
        }

        // Search for notification by device
        Collection<NotificationEntity> notifications =
                notificationRepository.findByDevice(screenEntity);
        Notification notification = findActiveNotification(notifications);

        if (screenEntity.getScreenGroup() != null && notification == null) {
            notifications =
                    notificationRepository.findByGroup(screenEntity.getScreenGroup());
            notification = findActiveNotification(notifications);
        }

        return notification;
    }

    private Notification findActiveNotification(Collection<NotificationEntity> notificationEntities) {
        Notification notification = null;
        for (NotificationEntity entity : notificationEntities) {
            long now = System.currentTimeMillis();
            if (entity.getFrom().getTime() < now &&
                    entity.getTo().getTime() >= now) {
                notification = new Notification();
                notification.setTitle(entity.getTitle());
                notification.setMessage(entity.getMessage());
                return notification;
            }
        }
        return notification;
    }

}
