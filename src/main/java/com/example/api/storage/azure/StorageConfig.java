package com.example.api.storage.azure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@Configuration
@ConfigurationProperties(prefix = "azure")
@Data
public class StorageConfig {
    private String accountName;
    private String accountKey;
    private String endpoint;
}
