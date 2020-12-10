package com.example.sampleapp.controller;

import com.example.sampleapp.model.KeyValue;
import com.example.sampleapp.service.KeyValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author walid.sewaify
 * @since 13-Nov-20
 */
@RestController
@RequestMapping("/store")
@Slf4j
public class KeyValueStoreController {
    private final KeyValueService service;

    public KeyValueStoreController(KeyValueService service) {
        this.service = service;
    }

    @GetMapping("/record")
    public String getValue(String key) {
        log.info("finding record for key {}", key);
        return service.getValue(key);
    }

    @PostMapping("/record")
    public void sayHello(@RequestBody KeyValue record) {
        service.store(record);
    }
}
