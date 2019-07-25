package com.stackroute.muzixapp.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class WebConfiguration {

    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new WebServlet());
        servletRegistrationBean.addUrlMappings("/console/*");
        return servletRegistrationBean;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {



    }
}
