package com.mortun.magicrituals.research;

import net.minecraft.resources.Identifier;

import java.util.Arrays;

public enum TraceType {
    FLOW("flow"),
    HEAT("heat"),
    GROWTH("growth");

    private final Identifier id;

    TraceType(String path) {
        this.id = Identifier.fromNamespaceAndPath("magicrituals", path);
    }

    public Identifier id() {
        return id;
    }

    public String asString() {
        return id.toString();
    }

    public static TraceType fromId(Identifier id) {
        return Arrays.stream(values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown trace type id: " + id));
    }
}
