package org.worldbridge.development.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("current_url")
    private String currentUrl;
    @JsonProperty("screen_details")
    private ScreenDetails screenDetails;

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

    public ScreenDetails getScreenDetails() {
        return screenDetails;
    }

    public void setScreenDetails(ScreenDetails screenDetails) {
        this.screenDetails = screenDetails;
    }
}
