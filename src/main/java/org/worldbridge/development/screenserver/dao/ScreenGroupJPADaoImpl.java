package org.worldbridge.development.screenserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.worldbridge.development.screenserver.domain.DeviceDetails;
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

    @Autowired
    private DeviceDetailsConverter deviceDetailsConverter;

    @Override
    public List<ScreenGroup> listScreenGroups() {
        Iterable<ScreenGroupEntity> entities = screenGroupRepository.findAll();

        List<ScreenGroup> groups = new ArrayList<>();
        for (ScreenGroupEntity entity : entities) {
            ScreenGroup screenGroup = new ScreenGroup();
            screenGroup.setGroupName(entity.getGroupName());
            screenGroup.setDevices(new ArrayList<>());
            for (ScreenEntity screenEntity : entity.getScreenEntities()) {
                DeviceDetails deviceDetails = deviceDetailsConverter.getDeviceDetails(screenEntity);
                screenGroup.getDevices().add(deviceDetails);
            }
            groups.add(screenGroup);
        }

        Collection<ScreenEntity> unassigned = screenRepository.findByScreenGroup(null);
        if (!unassigned.isEmpty()) {
            ScreenGroup screenGroup = new ScreenGroup();
            screenGroup.setGroupName("Unassigned");
            screenGroup.setDevices(new ArrayList<>());
            for (ScreenEntity entity : unassigned) {
                DeviceDetails deviceDetails = deviceDetailsConverter.getDeviceDetails(entity);
                screenGroup.getDevices().add(deviceDetails);
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
    public void addScreenToScreenGroup(String groupName, String screenId) throws DaoException {
        ScreenGroupEntity entity = screenGroupRepository.findOne(groupName);
        if (entity == null) {
            throw new DaoException("Group " + groupName + " doesn't exist");
        }

        // The screen is the "owner" of the relation ship
        ScreenEntity screenEntity = screenRepository.findOne(screenId);
        if (screenEntity == null) {
            throw new DaoException("Screen with id  " + screenId + " doesn't exist");
        }

        screenEntity.setScreenGroup(entity);
        screenRepository.save(screenEntity);
    }

    @Override
    public void removeScreenFromScreenGroup(String groupName, String screenId) throws DaoException {
        ScreenGroupEntity entity = screenGroupRepository.findOne(groupName);
        if (entity == null) {
            throw new DaoException("Group " + groupName + " doesn't exist");
        }

        // The screen is the "owner" of the relation ship
        ScreenEntity screenEntity = screenRepository.findOne(screenId);
        if (screenEntity == null) {
            throw new DaoException("Screen with id  " + screenId + " doesn't exist");
        }

        screenEntity.setScreenGroup(null);
        screenRepository.save(screenEntity);
    }

}
