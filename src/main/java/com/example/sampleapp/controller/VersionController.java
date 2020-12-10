package com.example.sampleapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author walid.sewaify
 * @since 14-Nov-20
 */
@RestController
public class VersionController {
    private final String commitId;

    public VersionController(@Value("${git.commit.id.abbrev:N/A}") String commitId) {
        this.commitId = commitId;
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig
            = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }

    @GetMapping("/version")
    public String version() {
        return commitId;
    }
}
