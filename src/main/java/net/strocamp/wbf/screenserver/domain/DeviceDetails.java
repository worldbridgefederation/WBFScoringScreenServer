package net.strocamp.wbf.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.strocamp.wbf.screenserver.CustomDateConverter;

import java.util.Date;

public class DeviceDetails {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("ip_address")
    private String ipAddress;
    @JsonProperty("last_seen")
    @JsonSerialize(using=CustomDateConverter.class)
    private Date lastSeen;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
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
