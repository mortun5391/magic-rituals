package com.mortun.magicrituals.rituals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface Ritual {
    String id();

    RitualPattern pattern();

    void execute(Level level, BlockPos center, Player player);
}
