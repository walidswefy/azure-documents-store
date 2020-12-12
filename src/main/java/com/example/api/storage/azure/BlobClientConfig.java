package com.example.api.storage.azure;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.example.api.storage.security.AccessKeyService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@Configuration
@AllArgsConstructor
public class BlobClientConfig {
    private final StorageConfig storageConfig;

    @Bean
    BlobServiceClient getBlobServiceClient() {
        // Create a blobServiceClient
        return new BlobServiceClientBuilder()
            .credential(new StorageSharedKeyCredential(storageConfig.getAccountName(), storageConfig.getAccountKey()))
            .endpoint(storageConfig.getEndpoint())
            .buildClient();
    }

    @Bean
    AccessKeyService getAccessKeyService() {
        return new AccessKeyService(storageConfig.getAccountKey());
    }
}
