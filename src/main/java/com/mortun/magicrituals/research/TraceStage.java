package com.mortun.magicrituals.research;

import java.util.Arrays;
import java.util.Locale;

/**
 * TraceStage -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-08
 */
public enum TraceStage {
    SOURCE("source"),
    RAW_SAMPLE("raw_sample"),
    PROCESSED_FRAGMENT("processed_fragment"),
    KNOWLEDGE("knowledge");

    private final String serializedName;

    TraceStage(String serializedName) {
        this.serializedName = serializedName;
    }

    public String serializedName() {
        return serializedName;
    }

    public static TraceStage fromSerializedName(String serializedName) {
        String normalized = serializedName.toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(stage -> stage.serializedName.equals(normalized))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown trace stage: " + serializedName));
    }
}
