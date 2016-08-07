package net.strocamp.wbf.screenserver.rest;

import net.strocamp.wbf.screenserver.domain.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/screens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatusService {
    private Log log = LogFactory.getLog(StatusService.class);

    @POST
    public Response status(@Context HttpServletRequest req, Status status) {
        String remoteAddr = req.getRemoteAddr();
        log.info("Received update from " + status.getDeviceId() + " at " + remoteAddr);
        return Response.accepted().build();
    }
}
