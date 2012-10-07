package org.metabuild.grobot.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring WebMVC configuration
 * 
 * @author jburbridge
 * @since 9/30/2012
 */
@Configuration
@EnableWebMvc
@Import(AppConfig.class)
@ComponentScan(basePackages = "org.metabuild.grobot.webapp.controllers")
public class WebConfig {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver ir = new InternalResourceViewResolver();
        ir.setPrefix("/WEB-INF/jsp/");
        ir.setSuffix(".jsp");
        return ir;
    }
}