package org.worldbridge.development.screenserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.worldbridge.development.screenserver.NotificationDateDeserializer;
import org.worldbridge.development.screenserver.NotificationDateSerializer;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class NotificationDetails {
    private int id;
    private String title;
    private String message;
    private String target;
    @JsonSerialize(using=NotificationDateSerializer.class)
    @JsonDeserialize(using= NotificationDateDeserializer.class)
    private Date validFrom;
    @JsonSerialize(using=NotificationDateSerializer.class)
    @JsonDeserialize(using= NotificationDateDeserializer.class)
    private Date validTo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
