package com.mortun.magicrituals.rituals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.joml.Vector3f;

public class RitualEffects {

    public static void playConsumeEffect(ServerLevel level, BlockPos pos) {
        DustParticleOptions redDust = new DustParticleOptions(
                0xFF0000,
                1.0f
        );
        level.sendParticles(
                redDust,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                20,
                0.25, 0.15, 0.25,
                0.01
        );
        level.playSound(
                null,
                pos,
                SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                SoundSource.BLOCKS,
                1.0f,
                1.1f
        );
    }

    public static void playCancelEffect(ServerLevel level, BlockPos pos) {
        DustParticleOptions grayDust = new DustParticleOptions(
                0x494747,
                1.0f
        );
        level.sendParticles(
                grayDust,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                20,
                0.25, 0.15, 0.25,
                0.01
        );
        level.playSound(
                null,
                pos,
                SoundEvents.NOTE_BLOCK_SNARE.value(),
                SoundSource.BLOCKS,
                1.0f,
                1.1f
        );

    }

    public static void playFailedActivationEffect(ServerLevel level, BlockPos pos) {
        DustParticleOptions grayDust = new DustParticleOptions(
                0x494747,
                1.0f
        );
        level.sendParticles(
                grayDust,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                20,
                0.25, 0.15, 0.25,
                0.01
        );
        level.playSound(
                null,
                pos,
                SoundEvents.NOTE_BLOCK_SNARE.value(),
                SoundSource.BLOCKS,
                1.0f,
                1.1f
        );

    }

    public static void playCompletedEffect(ServerLevel level, BlockPos pos) {
        DustParticleOptions grayDust = new DustParticleOptions(
                0xFFFFFF,
                1.0f
        );
        level.sendParticles(
                grayDust,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                20,
                0.25, 0.15, 0.25,
                0.01
        );
        level.playSound(
                null,
                pos,
                SoundEvents.LAVA_EXTINGUISH,
                SoundSource.BLOCKS,
                1.0f,
                1.1f
        );
    }

}
