package com.mortun.magicrituals.research;

import com.mortun.magicrituals.knowledge.KnowledgeId;
import net.minecraft.resources.Identifier;

import java.util.Objects;

/**
 * ResearchEntryId -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-08
 */
public class ResearchEntryId {
    private final Identifier id;

    private ResearchEntryId(Identifier id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public static ResearchEntryId of(String id) {
        return new ResearchEntryId(Identifier.fromNamespaceAndPath("magicrituals", id));
    }

    public static ResearchEntryId of(Identifier id) {
        return new ResearchEntryId(id);
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
        ResearchEntryId that = (ResearchEntryId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
