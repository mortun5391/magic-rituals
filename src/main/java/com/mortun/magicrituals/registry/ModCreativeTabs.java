package com.mortun.magicrituals.registry;

import com.mortun.magicrituals.MagicRituals;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * ModCreativeTabs -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-05
 */
public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicRituals.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.magicrituals.main"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.CHALK_RUNE.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CHALK_RUNE.get());
                        output.accept(ModItems.CHALK_RUNE_GOLD.get());
                        output.accept(ModItems.VEIL_DUST.get());
                        output.accept(ModItems.ECHO_SHARD.get());
                        output.accept(ModItems.TRACE_RESIN.get());
                        output.accept(ModItems.CHALK_SLATE.get());
                        output.accept(ModItems.CHALK_BASE.get());
                        output.accept(ModItems.RITUAL_CHALK.get());
                        output.accept(ModItems.GOLDEN_RITUAL_CHALK.get());
                        output.accept(ModItems.GUIDE_TO_THE_VEIL.get());
                        output.accept(ModItems.TRACE_NEEDLE.get());
                        output.accept(ModItems.RAW_FLOW_TRACE.get());
                        output.accept(ModItems.RAW_HEAT_TRACE.get());
                        output.accept(ModItems.SETTLED_FLOW_ESSENCE.get());
                        output.accept(ModItems.SETTLED_HEAT_ESSENCE.get());

                    })
                    .build());

    private ModCreativeTabs() {
    }
}
