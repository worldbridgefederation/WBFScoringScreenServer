package net.strocamp.wbf.screenserver;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.strocamp.wbf.screenserver.rest.CrashService;
import net.strocamp.wbf.screenserver.rest.StatusService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

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
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(springBus());
        endpoint.setAddress("/");

        endpoint.setServiceBeans(Arrays.asList(new StatusService(), new CrashService()));
        endpoint.setProviders(Arrays.asList(new JacksonJsonProvider()));
        return endpoint.create();
    }

}
