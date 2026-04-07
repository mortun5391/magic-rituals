package com.mortun.magicrituals.rituals.impl;

import com.mortun.magicrituals.MagicRituals;
import com.mortun.magicrituals.registry.ModBlocks;
import com.mortun.magicrituals.rituals.Ritual;
import com.mortun.magicrituals.rituals.RitualIngredient;
import com.mortun.magicrituals.rituals.RitualPattern;
import com.mortun.magicrituals.rituals.RitualRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public final class ClearSkiesRitual implements Ritual {
    @Override
    public String id() {
        return "clear_skies";
    }

    @Override
    public RitualPattern pattern() {
        return new RitualPattern(Map.ofEntries(
                Map.entry(BlockPos.ZERO, ModBlocks.CHALK_RUNE_GOLD.get()),
                Map.entry(new BlockPos(-1, 0, -1), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(0, 0, -1), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(1, 0, -1), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(-1, 0, 0), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(1, 0, 0), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(-1, 0, 1), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(0, 0, 1), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(1, 0, 1), ModBlocks.CHALK_RUNE.get()),
                Map.entry(new BlockPos(-1, -1, -1), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(0, -1, -1), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(1, -1, -1), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(-1, -1, 0), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(0, -1, 0), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(1, -1, 0), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(-1, -1, 1), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(0, -1, 1), Blocks.MAGMA_BLOCK),
                Map.entry(new BlockPos(1, -1, 1), Blocks.MAGMA_BLOCK)
        ));
    }

    @Override
    public RitualRecipe recipe() {
        return new RitualRecipe(List.of(
                new RitualIngredient(Items.BLAZE_POWDER, 4),
                new RitualIngredient(Items.GLOWSTONE_DUST, 8),
                new RitualIngredient(Items.SUNFLOWER, 2)
        ));
    }

    @Override
    public void execute(Level level, BlockPos center, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setWeatherParameters(12000, 0, false, false);
            MagicRituals.LOGGER.info("Executed ritual {} at {} in {}", id(), center, serverLevel.dimension());
        }
    }
}
