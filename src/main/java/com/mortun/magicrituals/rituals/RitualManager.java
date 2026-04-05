package com.mortun.magicrituals.rituals;

import com.mortun.magicrituals.rituals.impl.CrossRitual;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.List;

public final class RitualManager {
    private static final List<Ritual> RITUALS = List.of(
            new CrossRitual()
    );

    private RitualManager() {
    }

    public static Ritual findMatching(Level level, BlockPos center) {
        for (Ritual ritual : RITUALS) {
            if (RitualMatcher.matches(level, center, ritual.pattern())) {
                return ritual;
            }
        }

        return null;
    }

    public static String debugFirstMismatch(Level level, BlockPos center) {
        for (Ritual ritual : RITUALS) {
            return ritual.id() + ": " + RitualMatcher.describeFirstMismatch(level, center, ritual.pattern());
        }

        return "No rituals registered";
    }
}
