package com.example.sampleapp.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author walid.sewaify
 * @since 17-Nov-20
 */
class VersionControllerTest {

    @Test
    void simpleTest() {
        assertEquals(new VersionController("test").version(), "test");
    }
}
