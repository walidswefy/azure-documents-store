package com.example.sampleapp.service;

import com.example.sampleapp.model.KeyValue;
import com.example.sampleapp.repository.KeyValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author walid.sewaify
 * @since 13-Nov-20
 */
@Service
@Slf4j
public class KeyValueService {
    private final KeyValueRepository repository;

    public KeyValueService(KeyValueRepository repository) {
        this.repository = repository;
    }

    public void store(KeyValue record) {
        repository.save(record);
    }

    public String getValue(String key) {
        log.info("database record for key {}", key);
        Optional<KeyValue> message = repository.findById(key);
        return message.map(KeyValue::getValue).orElse(null);
    }
}
