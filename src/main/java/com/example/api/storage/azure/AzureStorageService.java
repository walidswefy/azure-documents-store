package com.example.api.storage.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobProperties;
import com.example.api.storage.exception.NamingException;
import com.example.api.storage.model.FileProperties;
import com.example.api.storage.security.AccessKeyService;
import com.example.api.storage.service.FileStorageService;
import com.microsoft.azure.storage.NameValidator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@Service
@Slf4j
@AllArgsConstructor
public class AzureStorageService implements FileStorageService {
    private final BlobServiceClient serviceClient;
    private final AccessKeyService accessKeyService;

    @SneakyThrows
    @Override
    public FileProperties uploadFile(String containerName, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        validateNames(containerName, fileName);

        log.info("uploading file {} to {}", fileName, containerName);
        BlobClient blobClient = asContainerClient(containerName).getBlobClient(fileName);
        String filePath = fileFromStream(file.getInputStream()).toPath().toString();
        blobClient.uploadFromFile(filePath, true);

        return buildFileProperties(containerName, fileName);
    }

    @Override
    public void deleteFile(String containerName, String fileName) {
        validateNames(containerName, fileName);
        log.info("deleting file {} from container {}", fileName, containerName);
        BlobClient blobClient = asContainerClient(containerName).getBlobClient(fileName);
        blobClient.delete();
    }

    @Override
    public byte[] downloadFile(String containerName, String fileName) {
        validateNames(containerName, fileName);
        log.info("downloading file {} from container {}", fileName, containerName);
        BlobClient blobClient = asContainerClient(containerName).getBlobClient(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public boolean verifyAccessKey(String containerName, String fileName, String accessKey) {
        return accessKeyService.secureHash(containerName + fileName).equals(accessKey);
    }

    @Override
    public List<FileProperties> listContainer(String containerName) {
        BlobContainerClient blobContainerClient = asContainerClient(containerName);
        return blobContainerClient.listBlobs().stream()
            .map(blob -> buildFileProperties(containerName, blob.getName())).collect(Collectors.toList());
    }

    private void validateNames(String containerName, String fileName) {
        try {
            NameValidator.validateContainerName(containerName);
            NameValidator.validateBlobName(fileName);
        } catch (IllegalArgumentException e) {
            log.warn("Azure non-compatible name", e);
            throw new NamingException(e.getMessage());
        }
    }

    private BlobContainerClient asContainerClient(String containerName) {
        BlobContainerClient containerClient = serviceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            log.info("container {} not found, creating container..", containerName);
            serviceClient.createBlobContainer(containerName);
        }
        return containerClient;
    }

    private File fileFromStream(InputStream in) throws IOException {
        final File tempFile = File.createTempFile("azure_", "_upload");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    private FileProperties buildFileProperties(String containerName, String fileName) {
        BlobClient blobClient = asContainerClient(containerName).getBlobClient(fileName);
        BlobProperties properties = blobClient.getProperties();
        return FileProperties.builder()
            .containerName(containerName).fileName(fileName).version(properties.getVersionId())
            .fileType(properties.getContentType()).size(properties.getBlobSize())
            .contentMd5(Base64.getEncoder().encodeToString(properties.getContentMd5()))
            .accessKey(accessKeyService.secureHash(containerName + fileName))
            .build();
    }
}
