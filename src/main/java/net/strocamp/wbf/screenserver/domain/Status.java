package net.strocamp.wbf.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty("device_id")
    private String deviceId;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
