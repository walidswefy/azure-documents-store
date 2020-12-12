package com.example.api.storage.controller;

import com.example.api.storage.exception.FileStorageException;
import com.example.api.storage.model.FileProperties;
import com.example.api.storage.model.view.FileResponse;
import com.example.api.storage.service.FileStorageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@RestController
@RequestMapping({"", "v1"})
@Slf4j
@RequiredArgsConstructor
@Data
public class FileStorageController {
    private final FileStorageService azureStorageService;

    @Value("${azure.maxFileSize}")
    private long maxFileSize = 20;

    @PutMapping("/upload/{containerName}")
    public FileResponse uploadFile(@PathVariable String containerName,
                                   @RequestBody MultipartFile file) {
        System.out.println(maxFileSize);
        if (file == null || file.getSize() > (maxFileSize * 1024 * 1024L)) {
            throw new FileStorageException("File is required within allowed maximum size " + maxFileSize + "MB");
        }

        FileProperties properties = azureStorageService.uploadFile(containerName, file);
        log.info("file uploaded successfully with following details : {}", properties);

        return toView(properties);
    }

    @PutMapping("/uploadMany/{containerName}")
    public List<FileResponse> uploadMultipleFiles(@PathVariable String containerName,
                                                  @RequestBody MultipartFile[] files) {
        return Arrays.stream(files).map(file -> uploadFile(containerName, file)).collect(Collectors.toList());
    }

    /**
     * Resource must be secured on API Gateway level
     */
    @GetMapping("/download/{containerName}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String containerName,
                                                 @PathVariable String fileName) {
        byte[] resource = azureStorageService.downloadFile(containerName, fileName);

        String contentType = Optional.of(URLConnection.guessContentTypeFromName(fileName))
            .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName))
            .body(new InputStreamResource(new ByteArrayInputStream(resource)));
    }

    /**
     * Public download to file using access key
     */
    @GetMapping("/shared/{containerName}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String containerName, @PathVariable String fileName,
                                                 @RequestParam String accessKey) {
        if (azureStorageService.verifyAccessKey(containerName, fileName, accessKey)) {
            return downloadFile(containerName, fileName);
        }
        log.warn("invalid access attempt");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found");
    }

    @GetMapping("/list/{containerName}")
    public List<FileProperties> listFiles(@PathVariable String containerName) {
        return azureStorageService.listContainer(containerName)
            .stream().map(this::toView).collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{containerName}/{fileName}")
    public void deleteFile(@PathVariable String containerName, @PathVariable String fileName) {
        azureStorageService.deleteFile(containerName, fileName);
    }

    private FileResponse toView(FileProperties properties) {
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/shared/")
            .pathSegment(properties.getContainerName(), properties.getFileName())
            .queryParam("accessKey", properties.getAccessKey()).toUriString();

        return new FileResponse(properties, downloadUri);
    }
}
