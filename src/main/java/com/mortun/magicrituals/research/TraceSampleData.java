package com.mortun.magicrituals.research;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import java.util.Objects;

/**
 * TraceSampleData -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-08
 */
public record TraceSampleData(TraceType type, TraceStage stage, Identifier sourceId) {
    public static final String TYPE_KEY = "type";
    public static final String STAGE_KEY = "stage";
    public static final String SOURCE_ID_KEY = "source_id";

    public TraceSampleData {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(stage, "stage");
        Objects.requireNonNull(sourceId, "sourceId");
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString(TYPE_KEY, type.asString());
        tag.putString(STAGE_KEY, stage.serializedName());
        tag.putString(SOURCE_ID_KEY, sourceId.toString());
        return tag;
    }

    public static TraceSampleData fromTag(CompoundTag tag) {
        String typeId = tag.getString(TYPE_KEY)
                .orElseThrow(() -> new IllegalArgumentException("Missing trace type in tag"));
        String stageId = tag.getString(STAGE_KEY)
                .orElseThrow(() -> new IllegalArgumentException("Missing trace stage in tag"));
        String sourceIdValue = tag.getString(SOURCE_ID_KEY)
                .orElseThrow(() -> new IllegalArgumentException("Missing trace source id in tag"));

        TraceType type = TraceType.fromId(Identifier.parse(typeId));
        TraceStage stage = TraceStage.fromSerializedName(stageId);
        Identifier sourceId = Identifier.parse(sourceIdValue);
        return new TraceSampleData(type, stage, sourceId);
    }

    public TraceSampleData withStage(TraceStage stage) {
        return new TraceSampleData(type, stage, sourceId);
    }
}
