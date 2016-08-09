package org.worldbridge.development.screenserver.persistance;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class ScreenGroupEntity {
    @Id
    @Column(name = "group_name")
    private String groupName;

    @OneToMany(targetEntity = ScreenEntity.class, cascade = CascadeType.DETACH, fetch=FetchType.EAGER, mappedBy = "screenGroup")
    private Collection<ScreenEntity> screenEntities;

    protected ScreenGroupEntity() {
        // JPA requirement
    }

    public ScreenGroupEntity(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Collection<ScreenEntity> getScreenEntities() {
        return screenEntities;
    }

    public void setScreenEntities(Collection<ScreenEntity> screenEntities) {
        this.screenEntities = screenEntities;
    }
}
