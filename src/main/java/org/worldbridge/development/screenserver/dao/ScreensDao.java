package org.worldbridge.development.screenserver.dao;

import org.worldbridge.development.screenserver.domain.DeviceDetails;

import java.util.List;

public interface ScreensDao {
    List<DeviceDetails> listScreens();

    void storeScreen(DeviceDetails deviceDetails);
}
