package net.strocamp.wbf.screenserver.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;

@Path("/reportcrash")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class CrashService {
    private Log log = LogFactory.getLog(CrashService.class);

    @POST
    public void parseCrashData(MultivaluedMap<String, String> formData) {
        for (Map.Entry<String, List<String>> entry : formData.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (String value : entry.getValue()) {
                sb.append(value);
                sb.append(';');
            }
            log.info(entry.getKey() + ":" + sb.toString());
        }
    }
}
