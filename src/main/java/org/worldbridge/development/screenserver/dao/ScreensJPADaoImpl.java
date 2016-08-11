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

    @Autowired
    private DeviceDetailsConverter deviceDetailsConverter;

    @Override
    public List<DeviceDetails> listScreens() {
        Iterable<ScreenEntity> devices = screenRepository.findAll();
        List<DeviceDetails> result = new ArrayList<>();
        for (ScreenEntity device : devices) {
            DeviceDetails details = deviceDetailsConverter.getDeviceDetails(device);
            result.add(details);
        }
        return result;
    }

    @Override
    public void storeScreen(DeviceDetails deviceDetails) {
        ScreenEntity screenEntity = screenRepository.findOne(deviceDetails.getDeviceId());
        if (screenEntity == null) {
            // New Screen
            screenEntity = new ScreenEntity(deviceDetails.getDeviceId());
        }

        screenEntity.setCurrentUrl(deviceDetails.getCurrentUrl());
        screenEntity.setIpAddress(deviceDetails.getIpAddress());
        screenEntity.setLastSeen(deviceDetails.getLastSeen());
        screenEntity.setResolution(deviceDetailsConverter.getResolution(deviceDetails));

        screenEntity.setManufacturer(deviceDetails.getManufacturer());
        screenEntity.setModel(deviceDetails.getModel());
        screenEntity.setVersion(deviceDetails.getVersion());
        screenEntity.setSerial(deviceDetails.getSerial());
        screenEntity.setAppVersion(deviceDetails.getAppVersion());

        screenRepository.save(screenEntity);
    }

    @Override
    public DeviceDetails getScreen(String androidId) {
        ScreenEntity entity = screenRepository.findOne(androidId);
        return deviceDetailsConverter.getDeviceDetails(entity);
    }

    public String getGroupForScreen(String deviceId) {
        ScreenEntity entity = screenRepository.findOne(deviceId);
        String groupName = null;
        if (entity != null) {
            groupName = entity.getScreenGroup() != null ? entity.getScreenGroup().getGroupName() : null;
        }
        return groupName;
    }


}
