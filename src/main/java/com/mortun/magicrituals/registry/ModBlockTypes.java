package com.mortun.magicrituals.registry;

import com.mojang.serialization.MapCodec;
import com.mortun.magicrituals.MagicRituals;
import com.mortun.magicrituals.block.ChalkRuneBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModBlockTypes {

    // Регистр block types (codec'ов) для кастомных Block subclasses
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, MagicRituals.MODID);

    // Codec для ChalkRuneBlock
    public static final Supplier<MapCodec<ChalkRuneBlock>> CHALK_RUNE =
            BLOCK_TYPES.register(
                    "chalk_rune",
                    () -> BlockBehaviour.simpleCodec(ChalkRuneBlock::new)
            );

    private ModBlockTypes() {
    }
}