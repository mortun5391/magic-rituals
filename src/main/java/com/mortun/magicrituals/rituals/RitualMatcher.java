package com.mortun.magicrituals.rituals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public final class RitualMatcher {
    private RitualMatcher() {
    }

    public static boolean matches(Level level, BlockPos center, RitualPattern pattern) {
        for (Map.Entry<BlockPos, Block> entry : pattern.requiredBlocks().entrySet()) {
            BlockPos targetPos = center.offset(entry.getKey());
            if (!level.getBlockState(targetPos).is(entry.getValue())) {
                return false;
            }
        }

        return true;
    }

    public static String describeFirstMismatch(Level level, BlockPos center, RitualPattern pattern) {
        for (Map.Entry<BlockPos, Block> entry : pattern.requiredBlocks().entrySet()) {
            BlockPos offset = entry.getKey();
            BlockPos targetPos = center.offset(offset);
            Block expectedBlock = entry.getValue();
            BlockState actualState = level.getBlockState(targetPos);

            if (!actualState.is(expectedBlock)) {
                String expectedId = BuiltInRegistries.BLOCK.getKey(expectedBlock).toString();
                String actualId = BuiltInRegistries.BLOCK.getKey(actualState.getBlock()).toString();
                return "Mismatch at " + offset + " -> expected " + expectedId + ", got " + actualId;
            }
        }

        return "Pattern matched";
    }
}
