package net.strocamp.wbf.screenserver.dao;

import net.strocamp.wbf.screenserver.domain.ScreenDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScreensHashmapDaoImpl implements ScreensDao {
    private final ConcurrentHashMap<String, ScreenDetails> screens = new ConcurrentHashMap<>();

    @Override
    public List<ScreenDetails> listScreens() {
        return new ArrayList<>(screens.values());
    }

    @Override
    public void storeScreen(ScreenDetails screenDetails) {
        screens.put(screenDetails.getDeviceId(), screenDetails);
    }
}
