package com.mortun.magicrituals.item;

import com.mortun.magicrituals.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.sounds.SoundSource;

public class RitualChalk extends Item {
    public RitualChalk(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }

        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        Level level = placeContext.getLevel();
        BlockPos placePos = placeContext.getClickedPos();
        BlockState placeState = ModBlocks.CHALK_RUNE.get().getStateForPlacement(placeContext);

        if (placeState == null) {
            return InteractionResult.PASS;
        }

        if (!level.getBlockState(placePos).canBeReplaced(placeContext)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!level.setBlock(placePos, placeState, 3)) {
            return InteractionResult.FAIL;
        }

        Player player = placeContext.getPlayer();
        ItemStack stack = placeContext.getItemInHand();

        SoundType sound = placeState.getSoundType(level, placePos, player);
        level.playSound(
                player,
                placePos,
                sound.getPlaceSound(),
                SoundSource.BLOCKS,
                (sound.getVolume() + 1.0F) / 2.0F,
                sound.getPitch() * 0.8F
        );
        level.gameEvent(player, GameEvent.BLOCK_PLACE, placePos);

        stack.setDamageValue(stack.getDamageValue() + 1);
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.shrink(1);
        }
        // 1.21.11: no broadcastBreakEvent on Player; simple damage update is enough

        return InteractionResult.SUCCESS;
    }
}
