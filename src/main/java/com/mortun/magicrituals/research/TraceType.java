package com.mortun.magicrituals.research;

import net.minecraft.resources.Identifier;

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
}
