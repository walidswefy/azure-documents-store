package com.example.sampleapp.repository;

import com.example.sampleapp.model.KeyValue;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author walid.sewaify
 * @since 13-Nov-20
 */
public interface KeyValueRepository extends MongoRepository<KeyValue, String> {
}
