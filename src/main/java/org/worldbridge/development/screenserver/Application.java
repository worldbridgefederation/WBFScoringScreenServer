package org.worldbridge.development.screenserver;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.worldbridge.development.screenserver.rest.CrashService;
import org.worldbridge.development.screenserver.rest.NotificationService;
import org.worldbridge.development.screenserver.rest.ScreenGroupService;
import org.worldbridge.development.screenserver.rest.StatusService;

import java.util.Arrays;
import java.util.Collections;

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
    public CrashService crashService() {
        return new CrashService();
    }

    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(springBus());
        endpoint.setAddress("/");

        endpoint.setServiceBeans(Arrays.asList(statusService(), screenGroupService(), notificationService(),
                crashService()));
        endpoint.setProviders(Collections.singletonList(new JacksonJsonProvider()));

        return endpoint.create();
    }
}
