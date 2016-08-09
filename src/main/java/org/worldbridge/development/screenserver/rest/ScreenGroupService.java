package org.worldbridge.development.screenserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ScreenGroupDao screenGroupDao;

    @GET
    public List<ScreenGroup> list() {
        return screenGroupDao.listScreenGroups();
    }

    @PUT
    public Response addScreenGroup(ScreenGroup screenGroup) {
        screenGroupDao.createScreenGroup(screenGroup.getGroupName());

        return Response.accepted().build();
    }

    @PUT
    @Path("{groupName}/devices")
    public Response addDeviceToGroup(@PathParam("groupName") String groupName, DeviceDetails deviceDetails) {
        screenGroupDao.addScreenToScreenGroup(groupName, deviceDetails.getDeviceId());

        return Response.accepted().build();
    }

    @DELETE
    @Path("{groupName/devices")
    public Response removeDeviceFromGroup(@PathParam("groupName") String groupName, DeviceDetails deviceDetails) {
        screenGroupDao.removeScreenFromScreenGroup(groupName, deviceDetails.getDeviceId());

        return Response.accepted().build();
    }
}
