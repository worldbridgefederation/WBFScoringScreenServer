package net.strocamp.wbf.screenserver.rest;

import net.strocamp.wbf.screenserver.dao.ScreensDao;
import net.strocamp.wbf.screenserver.domain.ScreenDetails;
import net.strocamp.wbf.screenserver.domain.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/screens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatusService {
    private Log log = LogFactory.getLog(StatusService.class);

    @Autowired
    private ScreensDao screensDao;

    @POST
    public Response status(@Context HttpServletRequest req, Status status) {
        String remoteAddr = req.getRemoteAddr();
        log.info("Received update from " + status.getDeviceId() + " at " + remoteAddr);

        ScreenDetails details = new ScreenDetails();
        details.setDeviceId(status.getDeviceId());
        details.setIpAddress(remoteAddr);
        details.setLastSeen(new Date());
        details.setCurrentUrl(status.getCurrentUrl());

        screensDao.storeScreen(details);

        return Response.accepted().build();
    }

    @GET
    public List<ScreenDetails> overview() {
        return screensDao.listScreens();
    }
}
