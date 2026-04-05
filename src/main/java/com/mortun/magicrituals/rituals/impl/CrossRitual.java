package com.mortun.magicrituals.rituals.impl;

import com.mortun.magicrituals.registry.ModBlocks;
import com.mortun.magicrituals.rituals.Ritual;
import com.mortun.magicrituals.rituals.RitualPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public final class CrossRitual implements Ritual {
    @Override
    public String id() {
        return "cross";
    }

    @Override
    public RitualPattern pattern() {
        return new RitualPattern(Map.ofEntries(
                Map.entry(BlockPos.ZERO, ModBlocks.CHALK_RUNE_GOLD.get()),
                Map.entry(new BlockPos(0, 0, -2), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(0, 0, 2), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(2, 0, 0), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(-2, 0, 0), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(1, -1, 0), Blocks.WATER),
                Map.entry(new BlockPos(0, -1, 1), Blocks.WATER),
                Map.entry(new BlockPos(1, -1, 1), Blocks.WATER),
                Map.entry(new BlockPos(-1, -1, 0), Blocks.WATER),
                Map.entry(new BlockPos(0, -1, -1), Blocks.WATER),
                Map.entry(new BlockPos(1, -1, -1), Blocks.WATER),
                Map.entry(new BlockPos(-1, -1, 1), Blocks.WATER),
                Map.entry(new BlockPos(-1, -1, -1), Blocks.WATER)
        ));
    }



    @Override
    public void execute(Level level, BlockPos center, Player player) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dz = -1; dz <= 1; ++dz) {
                if (dx == 0 && dz == 0) continue;
                BlockPos targetPos = center.offset(dx, -1, dz);
                if (level.getBlockState(targetPos).is(Blocks.WATER)) {
                    level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(0, 12000, true, false);
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.literal("Cross ritual execute() called"));
        }
    }

}
