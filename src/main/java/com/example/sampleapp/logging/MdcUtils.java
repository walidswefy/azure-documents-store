package com.example.sampleapp.logging;

import org.slf4j.MDC;

/**
 * @author walid.sewaify
 * @since 2020-03-19
 */
public abstract class MdcUtils {

    private static final String CORRELATION_ID = "correlation_id";

    /**
     * Sets correlation id in log messages
     *
     * @param correlationId application id
     */
    public static void setCorrId(String correlationId) {
        MDC.put(CORRELATION_ID, correlationId);
    }

    /**
     * remove data from context
     */
    public static void clearCorrId() {
        MDC.remove(CORRELATION_ID);
    }
}
