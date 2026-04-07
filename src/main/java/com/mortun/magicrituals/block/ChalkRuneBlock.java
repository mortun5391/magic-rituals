package com.mortun.magicrituals.block;

import com.mojang.serialization.MapCodec;
import com.mortun.magicrituals.MagicRituals;
import com.mortun.magicrituals.registry.ModBlockTypes;
import com.mortun.magicrituals.registry.ModBlocks;
import com.mortun.magicrituals.rituals.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ChalkRuneBlock extends Block {

    private static final double ITEM_SEARCH_RADIUS = 1.0;
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 0.5, 16.0);

    public ChalkRuneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends Block> codec() {
        return ModBlockTypes.CHALK_RUNE.get();
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return Shapes.empty();
    }


    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return Block.canSupportCenter(level, below, Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess tickAccess,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }


    @Override
    protected InteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {

        if (hand == InteractionHand.MAIN_HAND && stack.isEmpty()) {
            return tryActivateRitual(level, pos, player);
        }

        return InteractionResult.PASS;
    }


    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {
        return tryActivateRitual(level, pos, player);
    }

    private InteractionResult tryActivateRitual(Level level, BlockPos pos, Player player) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        if (!level.getBlockState(pos).is(ModBlocks.CHALK_RUNE_GOLD.get())) {
            sendFailureMessage(player, "Ритуал запускается только с золотой руны.");
            return InteractionResult.SUCCESS;
        }

        if (ActiveRitualManager.isActive(level, pos)) {
            sendFailureMessage(player, "Этот ритуал уже активен.");
            return InteractionResult.SUCCESS;
        }

        AABB box = new AABB(pos).inflate(ITEM_SEARCH_RADIUS, 1.0, ITEM_SEARCH_RADIUS);
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, box, item ->
                item.isAlive() && !item.getItem().isEmpty());

        Ritual ritual = RitualManager.findMatching(level, pos, itemEntities);

        if (ritual != null) {
            RitualSession session = new RitualSession(pos, ritual, player);
            if (!ActiveRitualManager.start(serverLevel, session)) {
                sendFailureMessage(player, "Этот ритуал уже активен.");
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.SUCCESS;
        }

        String debugMessage = RitualManager.debugActivationFailure(level, pos, itemEntities);
        MagicRituals.LOGGER.info("Ritual activation failed at {} in {}: {}", pos, level.dimension(), debugMessage);


        if (RitualManager.hasMatchingStructure(level, pos)) {
            sendFailureMessage(player, "Недостаточно или неверно разложены предметы для ритуала.");
        } else {
            sendFailureMessage(player, "Ритуальная конструкция не подходит.");
        }

        RitualEffects.playFailedActivationEffect(serverLevel, pos);

        return InteractionResult.PASS;
    }

    private static void sendFailureMessage(Player player, String message) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.RED));
        }
    }

}
