package com.example.sampleapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author walid.sewaify
 * @since 13-Nov-20
 */
@Document("key_value_store")
@Data
public class KeyValue {
    @Id
    private String key;
    private String value;
}
