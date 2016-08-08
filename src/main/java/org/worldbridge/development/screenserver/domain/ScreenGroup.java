package org.worldbridge.development.screenserver.domain;

import java.util.List;

public class ScreenGroup {
    private String groupName;
    private List<String> deviceIds;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }
}
