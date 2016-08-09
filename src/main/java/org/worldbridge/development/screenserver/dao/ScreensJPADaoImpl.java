package org.worldbridge.development.screenserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.worldbridge.development.screenserver.domain.DeviceDetails;
import org.worldbridge.development.screenserver.domain.ScreenDetails;
import org.worldbridge.development.screenserver.persistance.ScreenEntity;
import org.worldbridge.development.screenserver.persistance.ScreenRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class ScreensJPADaoImpl implements ScreensDao {
    @Autowired
    private ScreenRepository screenRepository;

    @Override
    public List<DeviceDetails> listScreens() {
        Iterable<ScreenEntity> devices = screenRepository.findAll();
        List<DeviceDetails> result = new ArrayList<>();
        for (ScreenEntity device : devices) {
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
            result.add(details);
        }
        return result;
    }

    @Override
    public void storeScreen(DeviceDetails deviceDetails) {
        String resolution = null;
        if (deviceDetails.getScreenDetails() != null) {
            resolution = Integer.toString(deviceDetails.getScreenDetails().getWidth()) + "x" +
                    Integer.toString(deviceDetails.getScreenDetails().getHeigth());
        }
        ScreenEntity screenEntity = new ScreenEntity(deviceDetails.getDeviceId(),
                deviceDetails.getIpAddress(),
                deviceDetails.getLastSeen(),
                deviceDetails.getCurrentUrl(),
                resolution,
                null
                );
        screenRepository.save(screenEntity);
    }
}
