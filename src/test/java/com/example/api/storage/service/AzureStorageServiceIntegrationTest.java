package com.example.api.storage.service;

import com.example.api.storage.azure.AzureStorageService;
import com.example.api.storage.model.FileProperties;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@SpringBootTest
@Tag("IT")
class AzureStorageServiceIntegrationTest {

    @Autowired
    private AzureStorageService azureStorageService;

    @Test
    void testUploadDownloadDelete() {
        String fileContent = "Hello, World!";
        String containerName = "test-container";
        String fileName = "hello.txt";

        MockMultipartFile file = new MockMultipartFile(
            "file", fileName, MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());

        FileProperties properties = azureStorageService.uploadFile(containerName, file);
        assertEquals(containerName, properties.getContainerName());

        byte[] downloadedFile = azureStorageService.downloadFile(containerName, fileName);
        assertEquals(fileContent, new String(downloadedFile));

        azureStorageService.deleteFile(containerName, fileName);
    }
}
