package net.strocamp.wbf.screenserver.dao;

import net.strocamp.wbf.screenserver.domain.DeviceDetails;

import java.util.List;

public interface ScreensDao {
    List<DeviceDetails> listScreens();

    void storeScreen(DeviceDetails deviceDetails);
}
