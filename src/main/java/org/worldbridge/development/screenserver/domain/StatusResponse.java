package org.worldbridge.development.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class StatusResponse {
    private Boolean showNotitification;
    private Notification notification;

    public Boolean getShowNotitification() {
        return showNotitification;
    }

    public void setShowNotitification(Boolean showNotitification) {
        this.showNotitification = showNotitification;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
