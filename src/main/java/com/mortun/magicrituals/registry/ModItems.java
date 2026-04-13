package com.mortun.magicrituals.registry;

import com.mortun.magicrituals.MagicRituals;
import com.mortun.magicrituals.item.GoldenRitualChalk;
import com.mortun.magicrituals.item.GuideToTheVeil;
import com.mortun.magicrituals.item.RitualChalk;
import com.mortun.magicrituals.item.TraceNeedle;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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
            ITEMS.registerSimpleBlockItem("chalk_rune", ModBlocks.CHALK_RUNE, properties -> properties);

    public static final DeferredItem<BlockItem> CHALK_RUNE_GOLD =
            ITEMS.registerSimpleBlockItem("chalk_rune_gold", ModBlocks.CHALK_RUNE_GOLD, properties -> properties);

    public static final DeferredItem<Item> VEIL_DUST =
            simple("veil_dust");

    public static final DeferredItem<Item> ECHO_SHARD =
            simple("echo_shard");

    public static final DeferredItem<Item> TRACE_RESIN =
            simple("trace_resin");

    public static final DeferredItem<Item> CHALK_SLATE =
            simple("chalk_slate");

    public static final DeferredItem<Item> CHALK_BASE =
            simple("chalk_base");

    public static final DeferredItem<RitualChalk> RITUAL_CHALK = item(
            "ritual_chalk",
            RitualChalk::new,
            properties -> properties.stacksTo(64)
    );

    public static final DeferredItem<GoldenRitualChalk> GOLDEN_RITUAL_CHALK = item(
            "golden_ritual_chalk",
            GoldenRitualChalk::new,
            properties -> properties.stacksTo(64)
    );

    public static final DeferredItem<GuideToTheVeil> GUIDE_TO_THE_VEIL = item(
            "guide_to_the_veil",
            GuideToTheVeil::new,
            properties -> properties.stacksTo(1)
    );

    public static final DeferredItem<TraceNeedle> TRACE_NEEDLE = item(
            "trace_needle",
            TraceNeedle::new,
            properties -> properties.stacksTo(1)
    );

    public static final DeferredItem<Item> RAW_FLOW_TRACE =
            stackable("raw_flow_trace", 16);

    public static final DeferredItem<Item> RAW_HEAT_TRACE =
            stackable("raw_heat_trace", 16);

    public static final DeferredItem<Item> SETTLED_FLOW_ESSENCE =
            stackable("settled_flow_essence", 16);

    public static final DeferredItem<Item> SETTLED_HEAT_ESSENCE =
            stackable("settled_heat_essence", 16);

    private static DeferredItem<Item> simple(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    private static DeferredItem<Item> stackable(String name, int stackSize) {
        return ITEMS.registerSimpleItem(name, properties -> properties.stacksTo(stackSize));
    }

    private static <T extends Item> DeferredItem<T> item(
            String name,
            java.util.function.Function<Item.Properties, T> factory,
            java.util.function.UnaryOperator<Item.Properties> properties
    ) {
        return ITEMS.registerItem(name, factory, properties);
    }

    private ModItems() {
    }
}
