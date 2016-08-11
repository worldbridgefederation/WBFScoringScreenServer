package org.worldbridge.development.screenserver.rest;

import org.springframework.beans.factory.parsing.ReaderContext;
import org.worldbridge.development.screenserver.dao.NotificationDao;
import org.worldbridge.development.screenserver.dao.ScreensDao;
import org.worldbridge.development.screenserver.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/screens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatusService {
    private Log log = LogFactory.getLog(StatusService.class);

    private ScreensDao screensDao;
    private NotificationDao notificationDao;

    @Autowired
    public void setScreensDao(ScreensDao screensDao) {
        this.screensDao = screensDao;
    }

    @Autowired
    public void setNotificationDao(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @POST
    public StatusResponse status(@Context HttpServletRequest req, Status status) {
        String remoteAddr = req.getRemoteAddr();
        if (status == null) {
            log.error("Received update from " + remoteAddr + " with empty status!");
            throw new BadRequestException("Status object shouldn't be empty");
        }

        log.info("Received update from " + status.getDeviceId() + " at " + remoteAddr);

        DeviceDetails details = new DeviceDetails();
        details.setDeviceId(status.getDeviceId());
        details.setIpAddress(remoteAddr);
        details.setLastSeen(new Date());
        details.setCurrentUrl(status.getCurrentUrl());
        details.setScreenDetails(status.getScreenDetails());

        if (status.getHardwareDetails() != null) {
            HardwareDetails hardwareDetails = status.getHardwareDetails();
            details.setManufacturer(hardwareDetails.getManufacturer());
            details.setModel(hardwareDetails.getModel());
            details.setVersion(hardwareDetails.getReleaseVersion());
            details.setSerial(hardwareDetails.getSerial());
        }

        if (status.getVersionDetails() != null) {
            VersionDetails versionDetails = status.getVersionDetails();
            details.setAppVersion(versionDetails.getVersionName() + " build "+ versionDetails.getVersionId() );
        }

        screensDao.storeScreen(details);

        Notification notification = notificationDao.getNotificationForDevice(details.getDeviceId());

        StatusResponse statusResponse = new StatusResponse();
        if (notification != null) {
            statusResponse.setShowNotitification(true);
            statusResponse.setNotification(notification);
        } else {
            statusResponse.setShowNotitification(false);
        }
        return statusResponse;
    }

    @GET
    public List<DeviceDetails> overview() {
        return screensDao.listScreens();
    }

    @GET
    @Path("{androidId}")
    public DeviceDetails getScreenByAndroidId(@PathParam("androidId") String androidId) {
        DeviceDetails device = screensDao.getScreen(androidId);
        if (device == null) {
            throw new NotFoundException();
        }
        return device;
    }
}
