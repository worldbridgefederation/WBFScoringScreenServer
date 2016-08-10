package org.worldbridge.development.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenGroup {
    private String groupName;
    private List<DeviceDetails> devices;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<DeviceDetails> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDetails> devices) {
        this.devices = devices;
    }
}
