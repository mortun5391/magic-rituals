package com.mortun.magicrituals.rituals;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.ChatFormatting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.UUID;

public class RitualSession {
    private static final int CONSUME_INTERVAL_TICKS = 23;
    private static final double ITEM_SEARCH_RADIUS = 1.0;

    private final BlockPos center;
    private final Ritual ritual;
    private final List<ItemStack> absorbedItems;
    private final Deque<Item> consumeQueue;
    private final UUID activatorId;
    private RitualSessionState state;
    private int ticksUntilNextConsume;

    public RitualSession(BlockPos center, Ritual ritual, Player activator) {
        this.center = center.immutable();
        this.ritual = ritual;
        this.absorbedItems = new ArrayList<>();
        this.consumeQueue = createConsumeQueue(ritual);
        this.activatorId = activator != null ? activator.getUUID() : null;
        this.state = RitualSessionState.WAITING_TO_CONSUME;
        this.ticksUntilNextConsume = CONSUME_INTERVAL_TICKS;
    }

    private static Deque<Item> createConsumeQueue(Ritual ritual) {
        Deque<Item> deque = new ArrayDeque<>();
        for (RitualIngredient ingredient : ritual.recipe().ingredients()) {
            for (int i = 0; i < ingredient.count(); ++i) {
                deque.add(ingredient.item());
            }
        }
        return deque;
    }

    public BlockPos center() {
        return center;
    }

    public Ritual ritual() {
        return ritual;
    }

    public List<ItemStack> absorbedItems() {
        return absorbedItems;
    }

    public Deque<Item> consumeQueue() {
        return consumeQueue;
    }

    public int ticksUntilNextConsume() {
        return ticksUntilNextConsume;
    }

    public RitualSessionState state() {
        return state;
    }

    public ServerPlayer activator(ServerLevel level) {
        return activatorId != null ? level.getServer().getPlayerList().getPlayer(activatorId) : null;
    }

    public boolean isTerminal() {
        return state == RitualSessionState.COMPLETED || state == RitualSessionState.CANCELLED;
    }

    public boolean isCompleted() {
        return state == RitualSessionState.COMPLETED;
    }

    public void setTicksUntilNextConsume(int ticksUntilNextConsume) {
        this.ticksUntilNextConsume = ticksUntilNextConsume;
    }

    public boolean canConsumeNow() {
        return state == RitualSessionState.WAITING_TO_CONSUME && ticksUntilNextConsume <= 0;
    }

    public void tick(ServerLevel level) {
        if (isTerminal()) {
            return;
        }

        if (!hasRemainingIngredients()) {
            markCompleted();
            return;
        }

        if (!canConsumeNow()) {
            tickCooldown();
            return;
        }

        markConsuming();

        Item item = peekNextIngredientUnit();
        ItemEntity itemEntity = findEntityToConsume(level, item);
        if (itemEntity == null) {
            cancel(level);
            return;
        }

        consumeOneItem(itemEntity);
        recordAbsorbedItem(item);
        popNextIngredientUnit();
        RitualEffects.playConsumeEffect(level, center);


        if (!hasRemainingIngredients()) {
            markCompleted();
            return;
        }

        resetConsumeCooldown();
        markWaiting();
    }


    public Item peekNextIngredientUnit() {
        return consumeQueue.peekFirst();
    }

    public Item popNextIngredientUnit() {
        return consumeQueue.pollFirst();
    }

    public boolean hasRemainingIngredients() {
        return !consumeQueue.isEmpty();
    }

    public void recordAbsorbedItem(Item item) {
        absorbedItems.add(new ItemStack(item, 1));
    }

    public void tickCooldown() {
        if (ticksUntilNextConsume > 0) {
            ticksUntilNextConsume--;
        }
    }

    public void resetConsumeCooldown() {
        this.ticksUntilNextConsume = CONSUME_INTERVAL_TICKS;
    }

    public void markConsuming() {
        this.state = RitualSessionState.CONSUMING;
    }

    public void markWaiting() {
        this.state = RitualSessionState.WAITING_TO_CONSUME;
    }

    public void markCompleted() {
        this.state = RitualSessionState.COMPLETED;
    }

    public void cancel(ServerLevel level) {
        cancel(level, "Ритуал прерван.");
    }

    public void cancel(ServerLevel level, String message) {
        state = RitualSessionState.CANCELLED;
        RitualEffects.playCancelEffect(level, center);
        refundAbsorbedItems(level);
        sendFailureMessage(level, message);
    }

    private void refundAbsorbedItems(ServerLevel level) {
        if (level.isClientSide()) return;

        RandomSource random = level.random;

        for (ItemStack stack : absorbedItems) {
            if (stack.isEmpty()) continue;

            double x = center.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            double y = center.getY() + 1.0;
            double z = center.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;

            ItemEntity itemEntity = new ItemEntity(level, x, y, z, stack.copy());

            double vx = (random.nextDouble() - 0.5) * 0.1;
            double vy = 0.1;
            double vz = (random.nextDouble() - 0.5) * 0.1;
            itemEntity.setDeltaMovement(vx, vy, vz);

            level.addFreshEntity(itemEntity);
        }

        absorbedItems.clear();
    }

    private ItemEntity findEntityToConsume(ServerLevel level, Item item) {
        AABB box = new AABB(center).inflate(ITEM_SEARCH_RADIUS, 1.0, ITEM_SEARCH_RADIUS);
        for (ItemEntity entity : level.getEntitiesOfClass(ItemEntity.class, box, itemEntity -> itemEntity.isAlive() && !itemEntity.getItem().isEmpty())) {
            ItemStack itemStack = entity.getItem();
            if (itemStack.getItem().equals(item)) {
                return entity;
            }
        }
        return null;
    }

    private void consumeOneItem(ItemEntity entity) {
        ItemStack itemStack = entity.getItem();
        itemStack.shrink(1);
        if (itemStack.getCount() == 0) {
            entity.discard();
        }
    }

    public void sendFailureMessage(ServerLevel level, String message) {
        ServerPlayer player = activator(level);
        if (player != null) {
            player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.RED));
        }
    }

}
