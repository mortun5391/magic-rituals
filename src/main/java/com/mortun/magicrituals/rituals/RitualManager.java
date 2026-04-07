package com.mortun.magicrituals.rituals;

import com.mortun.magicrituals.rituals.impl.ClearSkiesRitual;
import com.mortun.magicrituals.rituals.impl.RaincallerRitual;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public final class RitualManager {
    private static final List<Ritual> RITUALS = List.of(
            new RaincallerRitual(),
            new ClearSkiesRitual()
    );

    private RitualManager() {
    }

    public static Ritual findMatching(Level level, BlockPos center, List<ItemEntity> itemEntities) {
        for (Ritual ritual : RITUALS) {
            if (RitualMatcher.structureMatches(level, center, ritual.pattern()) &&
                RitualMatcher.recipeMatches(itemEntities, ritual.recipe())) {
                return ritual;
            }
        }

        return null;
    }

    public static boolean hasMatchingStructure(Level level, BlockPos center) {
        for (Ritual ritual : RITUALS) {
            if (RitualMatcher.structureMatches(level, center, ritual.pattern())) {
                return true;
            }
        }

        return false;
    }

    public static String debugActivationFailure(Level level, BlockPos center, List<ItemEntity> itemEntities) {
        String firstStructureMismatch = null;

        for (Ritual ritual : RITUALS) {
            if (RitualMatcher.structureMatches(level, center, ritual.pattern())) {
                return ritual.id() + ": " + RitualMatcher.describeRecipeMismatch(itemEntities, ritual.recipe());
            }

            if (firstStructureMismatch == null) {
                firstStructureMismatch = ritual.id() + ": " + RitualMatcher.describeFirstMismatch(level, center, ritual.pattern());
            }
        }

        return firstStructureMismatch != null ? firstStructureMismatch : "No rituals registered";
    }

    public static String debugFirstMismatch(Level level, BlockPos center) {
        for (Ritual ritual : RITUALS) {
            return ritual.id() + ": " + RitualMatcher.describeFirstMismatch(level, center, ritual.pattern());
        }

        return "No rituals registered";
    }
}
