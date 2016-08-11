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
    @Autowired
    private NotificationDao notificationDao;

    @GET
    public List<NotificationDetails> getNotifications() {
        return notificationDao.loadNotifications();
    }

    @PUT
    public Response addNotification(NotificationDetails notificationDetails) {

        try {
            notificationDao.addNotification(notificationDetails);
        } catch (DaoException e) {
            return Response.serverError().build();
        }
        return Response.accepted().build();
    }
}
