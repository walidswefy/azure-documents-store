package com.example.api.storage.service;

import com.example.api.storage.model.FileProperties;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author walid.sewaify
 * @since 14-Dec-20
 */
public interface FileStorageService {
    /**
     * Uploads a file to storage
     *
     * @param containerName folder/bucket for grouping set of files
     * @param file          content in Multi-part content type
     * @return file properties
     */
    FileProperties uploadFile(String containerName, MultipartFile file);

    /**
     * Marks a file for deletion. Response is http OK for successful operation
     *
     * @param containerName folder/bucket for grouping set of files
     * @param fileName      name of file
     */
    void deleteFile(String containerName, String fileName);

    /**
     * Download file content
     *
     * @param containerName folder/bucket for grouping set of files
     * @param fileName      name of file
     */
    byte[] downloadFile(String containerName, String fileName);

    /**
     * Marks a file for deletion. Response is http OK for successful operation
     *
     * @param containerName folder/bucket for grouping set of files
     * @param fileName      name of file
     * @param accessKey     provided key for public access of the file
     */
    boolean verifyAccessKey(String containerName, String fileName, String accessKey);

    /**
     * List set of files in a group
     *
     * @param containerName folder/bucket for grouping set of files
     */
    List<FileProperties> listContainer(String containerName);
}
