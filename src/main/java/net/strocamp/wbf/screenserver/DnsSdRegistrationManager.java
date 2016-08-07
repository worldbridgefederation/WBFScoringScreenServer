package net.strocamp.wbf.screenserver;

import com.apple.dnssd.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class DnsSdRegistrationManager implements RegisterListener {
    private Log log = LogFactory.getLog(DnsSdRegistrationManager.class);

    private static final String SERVER_NAME = "WBF_ScreenServer";

    @Value("${server.port}")
    private String serverPort;

    @PostConstruct
    public void registerServer() {
        TXTRecord txtRecord = new TXTRecord();
        txtRecord.set("version", "1");

        try {
            DNSSDRegistration register = DNSSD.register(0, DNSSD.ALL_INTERFACES,
                    SERVER_NAME, "_wbfscreens._tcp", null,
                    null, Integer.valueOf(serverPort),
                    txtRecord, this);
        } catch (DNSSDException e) {
            log.fatal("Failed to register service", e);
        }
    }

    @PreDestroy
    public void unregisterServer() {
        // This will be taken care of by the library when the application quits
    }

    @Override
    public void serviceRegistered(DNSSDRegistration dnssdRegistration, int i, String s, String s1, String s2) {
        log.info("Looks like the registration was ok");
    }

    @Override
    public void operationFailed(DNSSDService dnssdService, int i) {
        log.info("Looks like something went wrong");
    }
}
