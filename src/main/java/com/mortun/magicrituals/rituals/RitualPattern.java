package com.mortun.magicrituals.rituals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public record RitualPattern(Map<BlockPos, Block> requiredBlocks) {
}
