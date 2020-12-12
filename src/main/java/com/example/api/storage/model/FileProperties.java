package com.example.api.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileProperties {
    private String containerName;
    private String fileName;
    private String fileType;
    private long size;
    private String version;
    private String contentMd5;
    private String accessKey;
}
