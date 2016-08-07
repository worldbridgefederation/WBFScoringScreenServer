package net.strocamp.wbf.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("current_url")
    private String currentUrl;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }
}
