package com.mortun.magicrituals.registry;

import com.mortun.magicrituals.MagicRituals;
import com.mortun.magicrituals.block.ChalkRuneBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * ModBlocks -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-05
 */
public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MagicRituals.MODID);

    public static final DeferredBlock<ChalkRuneBlock> CHALK_RUNE = BLOCKS.registerBlock(
            "chalk_rune",
            ChalkRuneBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .noCollision()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.WOOL)
    );


    public static final DeferredBlock<ChalkRuneBlock> CHALK_RUNE_GOLD = BLOCKS.registerBlock(
            "chalk_rune_gold",
            ChalkRuneBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .noCollision()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.WOOL)
    );

    private ModBlocks() {
    }
}
