package org.worldbridge.development.screenserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.worldbridge.development.screenserver.dao.DaoException;
import org.worldbridge.development.screenserver.dao.NotificationDao;
import org.worldbridge.development.screenserver.domain.NotificationDetails;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationService {
    private NotificationDao notificationDao;

    @Autowired
    public void setNotificationDao(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @GET
    public List<NotificationDetails> getNotifications() {
        return notificationDao.loadNotifications();
    }

    @PUT
    public void addNotification(NotificationDetails notificationDetails) {
        if (notificationDetails == null) {
            throw new BadRequestException("notificationDetails shouldn't be empty");
        }

        if (notificationDetails.getMessage() == null || notificationDetails.getTitle() == null ||
                notificationDetails.getValidFrom() == null || notificationDetails.getValidTo() == null ||
                notificationDetails.getTarget() == null) {
            throw new BadRequestException("Missing required field");
        }

        try {
            notificationDao.addNotification(notificationDetails);
        } catch (DaoException e) {
            throw new BadRequestException(e);
        }
    }
}
