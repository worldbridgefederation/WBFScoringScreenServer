package org.worldbridge.development.screenserver.dao;

import org.worldbridge.development.screenserver.domain.ScreenGroup;

import java.util.List;

public interface ScreenGroupDao {
    List<ScreenGroup> listScreenGroups();

    void createScreenGroup(String groupName);

    void addScreenToScreenGroup(String groupName, String screenId);

    void removeScreenFromScreenGroup(String groupName, String screenId);
}
