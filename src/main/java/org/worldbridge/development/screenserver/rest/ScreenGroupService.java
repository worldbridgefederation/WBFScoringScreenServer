package org.worldbridge.development.screenserver.rest;

import org.worldbridge.development.screenserver.domain.ScreenGroup;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/screengroups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScreenGroupService {

    @GET
    public List<ScreenGroup> list() {
        List<ScreenGroup> result = new ArrayList<>();
        ScreenGroup a = new ScreenGroup();
        a.setGroupName("Presidents Office");
        a.setDeviceIds(Collections.singletonList("2806cc5d978bdd91"));

        ScreenGroup b = new ScreenGroup();
        b.setGroupName("Player Lobby");
        List<String> devices = new ArrayList<>();
        devices.add("e102fbfcb9136cbb");
        devices.add("fbfeafc76a116a91");
        devices.add("f2cb8761455e94c5");
        devices.add("afb6c78f56d277fe");
        b.setDeviceIds(devices);

        result.add(a);
        result.add(b);

        return result;
    }
}
