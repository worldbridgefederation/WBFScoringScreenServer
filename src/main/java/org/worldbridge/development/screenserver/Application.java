package org.worldbridge.development.screenserver;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.worldbridge.development.screenserver.hsqldb.HyperSqlDbServer;
import org.worldbridge.development.screenserver.rest.CrashService;
import org.worldbridge.development.screenserver.rest.NotificationService;
import org.worldbridge.development.screenserver.rest.ScreenGroupService;
import org.worldbridge.development.screenserver.rest.StatusService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/rest-api/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }


    @Bean
    public StatusService statusService() {
        return new StatusService();
    }

    @Bean
    public ScreenGroupService screenGroupService() {
        return new ScreenGroupService();
    }

    @Bean
    public NotificationService notificationService() {
        return new NotificationService();
    }

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(springBus());
        endpoint.setAddress("/");

        endpoint.setServiceBeans(Arrays.asList(statusService(), screenGroupService(), notificationService(),
                new CrashService()));
        endpoint.setProviders(Arrays.asList(new JacksonJsonProvider()));
        return endpoint.create();
    }
}
