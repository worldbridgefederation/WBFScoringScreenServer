package org.worldbridge.development.screenserver.hsqldb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;


public class HyperSqlDbServer implements SmartLifecycle {
    private Log log = LogFactory.getLog(HyperSqlDbServer.class);

    private Server server = null;
    private final HsqlProperties properties;

    private boolean running = false;

    @Value("${server.database.0}")
    private String database;

    @Value("${server.dbname.0}")
    private String dbname;

    @Value("${server.remote_open}")
    private String remoteOpen;

    @Value("${hsqldb.reconfig_logging}")
    private String reconfigLogging;

    public HyperSqlDbServer(Properties properties) {
        this.properties = new HsqlProperties(properties);
    }

    @Override
    public void start() {
        if (server == null) {
            server = new Server();
        }
        try {
            Properties lateWired = new Properties();
            lateWired.put("server.database.0", database);
            lateWired.put("server.dbname.0", dbname);
            lateWired.put("server.remote_open", remoteOpen);
            lateWired.put("hsqldb.reconfig_logging", reconfigLogging);
            properties.addProperties(lateWired);

            server.setProperties(properties);
            server.start();
            running = true;
        } catch (IOException | ServerAcl.AclFormatException e) {
            log.error("Error while starting hysql DB", e);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }

    @Override
    public boolean isRunning() {
        if (server != null) {
            server.checkRunning(running);
        }
        return running;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        stop();
        runnable.run();
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
