package org.worldbridge.development.screenserver.persistance;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ScreenEntity {
    @Id
    @Column(name="android_id", unique = true, nullable = false)
    private String androidId;

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="last_seen")
    private Date lastSeen;

    @Column(name="current_url", length = 1024)
    private String currentUrl;

    @Column(name="resolution")
    private String resolution;

    @Column(name="screen_size")
    private String screenSize;

    @Column(name="manufacturer")
    private String manufacturer;

    @Column(name="model")
    private String model;

    @Column(name="version")
    private String version;

    @Column(name="serial")
    private String serial;

    @Column(name="app_version")
    private String appVersion;

    @ManyToOne(targetEntity = ScreenGroupEntity.class, cascade = CascadeType.DETACH, fetch=FetchType.EAGER)
    @JoinColumn(name = "screen_group")
    private ScreenGroupEntity screenGroup;

    protected ScreenEntity() {
        // Required by spec
    }

    public ScreenEntity(String androidId) {
        this.androidId = androidId;
    }

    public ScreenEntity(String androidId, String ipAddress, Date lastSeen, String currentUrl, String resolution, String screenSize) {
        this.androidId = androidId;
        this.ipAddress = ipAddress;
        this.lastSeen = lastSeen;
        this.currentUrl = currentUrl;
        this.resolution = resolution;
        this.screenSize = screenSize;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public ScreenGroupEntity getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup(ScreenGroupEntity screenGroup) {
        this.screenGroup = screenGroup;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
