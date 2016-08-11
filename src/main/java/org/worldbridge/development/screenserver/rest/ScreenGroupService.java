package org.worldbridge.development.screenserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.worldbridge.development.screenserver.dao.DaoException;
import org.worldbridge.development.screenserver.dao.ScreenGroupDao;
import org.worldbridge.development.screenserver.domain.DeviceDetails;
import org.worldbridge.development.screenserver.domain.ScreenGroup;
import org.worldbridge.development.screenserver.persistance.ScreenGroupEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/screengroups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScreenGroupService {

    private ScreenGroupDao screenGroupDao;

    @Autowired
    public void setScreenGroupDao(ScreenGroupDao screenGroupDao) {
        this.screenGroupDao = screenGroupDao;
    }

    @GET
    public List<ScreenGroup> list() {
        return screenGroupDao.listScreenGroups();
    }

    @PUT
    public void addScreenGroup(ScreenGroup screenGroup) {
        if (screenGroup == null) {
            throw new BadRequestException();
        } else if (screenGroup.getDevices().size() != 0) {
            throw new BadRequestException("Adding a group with devices is not yet supported");
        }

        screenGroupDao.createScreenGroup(screenGroup.getGroupName());
    }

    @PUT
    @Path("{groupName}/devices")
    public void addDeviceToGroup(@PathParam("groupName") String groupName, DeviceDetails deviceDetails) {
        if (deviceDetails == null) {
            throw new BadRequestException("deviceDetails shouldn't be null");
        }

        try {
            screenGroupDao.addScreenToScreenGroup(groupName, deviceDetails.getDeviceId());
        } catch (DaoException e) {
            throw new BadRequestException(e);
        }
    }

    @DELETE
    @Path("{groupName}/devices")
    public void removeDeviceFromGroup(@PathParam("groupName") String groupName, DeviceDetails deviceDetails) {
        if (deviceDetails == null) {
            throw new BadRequestException("deviceDetails shouldn't be null");
        }

        try {
            screenGroupDao.removeScreenFromScreenGroup(groupName, deviceDetails.getDeviceId());
        } catch (DaoException e) {
            throw new BadRequestException(e);
        }
    }
}
