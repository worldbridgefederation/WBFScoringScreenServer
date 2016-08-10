package org.worldbridge.development.screenserver.dao;

import org.springframework.stereotype.Component;
import org.worldbridge.development.screenserver.domain.DeviceDetails;
import org.worldbridge.development.screenserver.domain.ScreenDetails;
import org.worldbridge.development.screenserver.persistance.ScreenEntity;

@Component
public class DeviceDetailsConverter {
    public DeviceDetails getDeviceDetails(ScreenEntity device) {
        DeviceDetails details = new DeviceDetails();
        details.setDeviceId(device.getAndroidId());
        details.setIpAddress(device.getIpAddress());
        details.setCurrentUrl(device.getCurrentUrl());
        details.setLastSeen(device.getLastSeen());

        ScreenDetails screenDetails = new ScreenDetails();
        if (device.getResolution() != null) {
            String[] resolution = device.getResolution().split("x");
            screenDetails.setWidth(Integer.parseInt(resolution[0]));
            screenDetails.setHeigth(Integer.parseInt(resolution[1]));
        }
        details.setScreenDetails(screenDetails);

        details.setManufacturer(device.getManufacturer());
        details.setModel(device.getModel());
        details.setVersion(device.getVersion());
        details.setAppVersion(device.getAppVersion());

        details.setDeviceStatus(assessDeviceStatus(device));

        return details;
    }


    public String getResolution(DeviceDetails deviceDetails) {
        String resolution = null;
        if (deviceDetails.getScreenDetails() != null) {
            resolution = Integer.toString(deviceDetails.getScreenDetails().getWidth()) + "x" +
                    Integer.toString(deviceDetails.getScreenDetails().getHeigth());
        }
        return resolution;
    }

    public String assessDeviceStatus(ScreenEntity entity) {
        String statusAssessment = "ok";

        // 5 minutes is critical, 30 seconds is warning
        long timeDiff = System.currentTimeMillis() - entity.getLastSeen().getTime();
        if (timeDiff > 300000) {
            statusAssessment = "critical";
        } else if (timeDiff > 30000) {
            statusAssessment = "warning";
        }

        // Fotiss not configured screen is warning
        if (entity.getCurrentUrl().contains("/tourn/scoringsystem/asp/messagedisplay.asp")) {
            if (!statusAssessment.equals("critical")) {
                statusAssessment = "warning";
            }
        }

        // Any error in the url is critical
        if (entity.getCurrentUrl().toLowerCase().contains("error")) {
            statusAssessment = "critical";
        }
        return statusAssessment;
    }
}
