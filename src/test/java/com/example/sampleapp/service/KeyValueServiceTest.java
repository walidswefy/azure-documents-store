package com.example.sampleapp.service;

import com.example.sampleapp.extensions.TestDataExtension;
import com.example.sampleapp.model.KeyValue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author walid.sewaify
 * @since 13-Nov-20
 */
@SpringBootTest
@ExtendWith(TestDataExtension.class)
@Tag("it")
class KeyValueServiceTest {
    @Autowired
    private KeyValueService service;

    @Test
    void testQueryInitData() {
        assertEquals("value1", service.getValue("key1"));
    }

    @Test
    void testStoreRetrieve() {
        KeyValue record = new KeyValue();
        record.setKey("key2");
        record.setValue("anotherValue");
        service.store(record);
        assertEquals(record.getValue(), service.getValue(record.getKey()));
    }
}
