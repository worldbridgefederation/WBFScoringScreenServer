package org.worldbridge.development.screenserver.persistance;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="message", length = 2048)
    private String message;

    @Column(name="date_from")
    private Date from;

    @Column(name="date_to")
    private Date to;

    @ManyToOne(targetEntity = ScreenEntity.class, cascade = CascadeType.DETACH)
    private ScreenEntity device;

    @ManyToOne(targetEntity = ScreenGroupEntity.class, cascade = CascadeType.DETACH)
    private ScreenGroupEntity group;

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

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public ScreenEntity getDevice() {
        return device;
    }

    public void setDevice(ScreenEntity device) {
        this.device = device;
    }

    public ScreenGroupEntity getGroup() {
        return group;
    }

    public void setGroup(ScreenGroupEntity group) {
        this.group = group;
    }
}
