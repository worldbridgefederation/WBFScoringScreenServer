package org.worldbridge.development.screenserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.worldbridge.development.screenserver.domain.Notification;
import org.worldbridge.development.screenserver.domain.NotificationDetails;
import org.worldbridge.development.screenserver.persistance.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class NotificationJPADaoImpl implements NotificationDao {
    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ScreenGroupRepository screenGroupRepository;

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

     @Override
     public List<NotificationDetails> loadNotifications() {
         Iterable<NotificationEntity> notifications = notificationRepository.findAll();
         List<NotificationDetails> result = new ArrayList<>();
         for (NotificationEntity notificationEntity : notifications) {
             NotificationDetails details = new NotificationDetails();
             details.setId(notificationEntity.getId());
             details.setValidFrom(notificationEntity.getFrom());
             details.setValidTo(notificationEntity.getTo());
             details.setTitle(notificationEntity.getTitle());
             details.setMessage(notificationEntity.getMessage());
             if (notificationEntity.getDevice() != null) {
                 details.setTarget(notificationEntity.getDevice().getAndroidId());
             } else if (notificationEntity.getGroup() != null) {
                 details.setTarget(notificationEntity.getGroup().getGroupName());
             }
             result.add(details);
         }
         return result;
     }

     @Override
     public void addNotification(NotificationDetails notificationDetails) throws DaoException {
         if (notificationDetails.getValidFrom().getTime() >= notificationDetails.getValidTo().getTime()) {
             throw new DaoException("Can't accept a from time after the to time");
         }

         if(notificationDetails.getValidTo().getTime() < System.currentTimeMillis()) {
             throw new DaoException("Useless to add an expired notification");
         }

         if (notificationDetails.getMessage() == null || notificationDetails.getMessage().isEmpty()) {
             throw new DaoException("Useless to add a notification with an empty message");
         }

         if (notificationDetails.getTitle() == null || notificationDetails.getTitle().isEmpty()) {
             throw new DaoException("Useless to add a notification with an empty message");
         }

         if (screenGroupRepository.findOne(notificationDetails.getTarget()) == null &&
                 screenRepository.findOne(notificationDetails.getTarget()) == null) {
             throw new DaoException("Target Not Found..");
         }

         NotificationEntity entity = new NotificationEntity();
         entity.setTitle(notificationDetails.getTitle());
         entity.setMessage(notificationDetails.getMessage());

         // Either one will be null and the other the target
         entity.setDevice(screenRepository.findOne(notificationDetails.getTarget()));
         entity.setGroup(screenGroupRepository.findOne(notificationDetails.getTarget()));

         entity.setFrom(notificationDetails.getValidFrom());
         entity.setTo(notificationDetails.getValidTo());

         notificationRepository.save(entity);
     }

}
