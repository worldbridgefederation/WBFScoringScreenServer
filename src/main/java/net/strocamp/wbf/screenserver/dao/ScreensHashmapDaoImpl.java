package net.strocamp.wbf.screenserver.dao;

import net.strocamp.wbf.screenserver.domain.DeviceDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScreensHashmapDaoImpl implements ScreensDao {
    private final ConcurrentHashMap<String, DeviceDetails> screens = new ConcurrentHashMap<>();

    @Override
    public List<DeviceDetails> listScreens() {
        return new ArrayList<>(screens.values());
    }

    @Override
    public void storeScreen(DeviceDetails deviceDetails) {
        screens.put(deviceDetails.getDeviceId(), deviceDetails);
    }
}
