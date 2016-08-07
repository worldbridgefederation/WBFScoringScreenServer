package net.strocamp.wbf.screenserver.dao;

import net.strocamp.wbf.screenserver.domain.ScreenDetails;

import java.util.List;

public interface ScreensDao {
    List<ScreenDetails> listScreens();

    void storeScreen(ScreenDetails screenDetails);
}
