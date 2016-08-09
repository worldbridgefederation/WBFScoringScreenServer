package org.worldbridge.development.screenserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.worldbridge.development.screenserver.domain.ScreenGroup;
import org.worldbridge.development.screenserver.persistance.ScreenEntity;
import org.worldbridge.development.screenserver.persistance.ScreenGroupEntity;
import org.worldbridge.development.screenserver.persistance.ScreenGroupRepository;
import org.worldbridge.development.screenserver.persistance.ScreenRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ScreenGroupJPADaoImpl implements ScreenGroupDao {

    @Autowired
    private ScreenGroupRepository screenGroupRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Override
    public List<ScreenGroup> listScreenGroups() {
        Iterable<ScreenGroupEntity> entities = screenGroupRepository.findAll();

        List<ScreenGroup> groups = new ArrayList<>();
        for (ScreenGroupEntity entity : entities) {
            ScreenGroup screenGroup = new ScreenGroup();
            screenGroup.setGroupName(entity.getGroupName());
            screenGroup.setDeviceIds(new ArrayList<>());
            for (ScreenEntity screenEntity : entity.getScreenEntities()) {
                screenGroup.getDeviceIds().add(screenEntity.getAndroidId());
            }
            groups.add(screenGroup);
        }

        Collection<ScreenEntity> unassigned = screenRepository.findByScreenGroup(null);
        if (!unassigned.isEmpty()) {
            ScreenGroup screenGroup = new ScreenGroup();
            screenGroup.setGroupName("Unassigned");
            screenGroup.setDeviceIds(new ArrayList<>());
            for (ScreenEntity entity : unassigned) {
                screenGroup.getDeviceIds().add(entity.getAndroidId());
            }
            groups.add(screenGroup);
        }

        return groups;
    }

    @Override
    public void createScreenGroup(String groupName) {
        ScreenGroupEntity screenGroupEntity = new ScreenGroupEntity(groupName);
        screenGroupRepository.save(screenGroupEntity);
    }

    @Override
    public void addScreenToScreenGroup(String groupName, String screenId) {
        ScreenGroupEntity entity = screenGroupRepository.findOne(groupName);

        // The screen is the "owner" of the relation ship
        ScreenEntity screenEntity = screenRepository.findOne(screenId);
        screenEntity.setScreenGroup(entity);
        screenRepository.save(screenEntity);
    }

    @Override
    public void removeScreenFromScreenGroup(String groupName, String screenId) {
        ScreenGroupEntity entity = screenGroupRepository.findOne(groupName);

        // The screen is the "owner" of the relation ship
        ScreenEntity screenEntity = screenRepository.findOne(screenId);
        screenEntity.setScreenGroup(null);
        screenRepository.save(screenEntity);
    }

}
