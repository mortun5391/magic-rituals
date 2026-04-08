package com.mortun.magicrituals.knowledge;

import net.minecraft.resources.Identifier;

import java.util.Objects;
/**
 * KnowledgeId -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-08
 */
public final class KnowledgeId {
    private final Identifier id;

    private KnowledgeId(Identifier id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public static KnowledgeId of(String id) {
        return new KnowledgeId(Identifier.fromNamespaceAndPath("magicrituals", id));
    }

    public static KnowledgeId of(Identifier id) {
        return new KnowledgeId(id);
    }

    public Identifier asIdentifier() {
        return id;
    }

    public String asString() {
        return id.toString();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnowledgeId that = (KnowledgeId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
