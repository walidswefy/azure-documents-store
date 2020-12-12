package com.example.api.storage.model.view;

import com.example.api.storage.model.FileProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author walid.sewaify
 * @since 14-Dec-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileResponse extends FileProperties {
    private final String shareLink;

    public FileResponse(FileProperties properties, String shareLink) {
        BeanUtils.copyProperties(properties, this);
        this.shareLink = shareLink;
    }
}
