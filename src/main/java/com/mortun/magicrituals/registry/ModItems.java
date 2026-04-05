package com.mortun.magicrituals.registry;

import com.mortun.magicrituals.MagicRituals;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * ModItems -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-05
 */
public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MagicRituals.MODID);

    public static final DeferredItem<BlockItem> CHALK_RUNE =
            ITEMS.registerSimpleBlockItem("chalk_rune_block", ModBlocks.CHALK_RUNE);

    private ModItems() {
    }
}
