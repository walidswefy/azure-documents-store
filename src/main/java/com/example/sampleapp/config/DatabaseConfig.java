package com.example.sampleapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * @author walid.sewaify
 * @since 15-Nov-20
 */
@Configuration
@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfig {


}
