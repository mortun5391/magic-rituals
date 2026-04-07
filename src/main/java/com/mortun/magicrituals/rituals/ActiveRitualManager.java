package com.mortun.magicrituals.rituals;

import com.mortun.magicrituals.MagicRituals;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ActiveRitualManager {
    private static final Map<RitualSite, RitualSession> ACTIVE_RITUALS = new HashMap<>();

    private ActiveRitualManager() {
    }

    public static boolean isActive(Level level, BlockPos center) {
        return ACTIVE_RITUALS.containsKey(new RitualSite(level.dimension(), center));
    }

    public static boolean start(ServerLevel level, RitualSession session) {
        RitualSite site = new RitualSite(level.dimension(), session.center());
        if (ACTIVE_RITUALS.containsKey(site)) {
            return false;
        }

        ACTIVE_RITUALS.put(site, session);
        return true;
    }

    public static void remove(Level level, BlockPos center) {
        ACTIVE_RITUALS.remove(new RitualSite(level.dimension(), center));
    }

    public static void tick(ServerLevel level) {
        List<RitualSite> toRemove = new ArrayList<>();

        for (Map.Entry<RitualSite, RitualSession> sessionEntry : ACTIVE_RITUALS.entrySet()) {
            RitualSite site = sessionEntry.getKey();
            if (!site.dimension().equals(level.dimension())) {
                continue;
            }

            RitualSession session = sessionEntry.getValue();
            session.tick(level);

            if (session.isCompleted()) {
                if (!RitualMatcher.structureMatches(level, session.center(), session.ritual().pattern())) {
                    MagicRituals.LOGGER.warn(
                            "Ritual {} at {} in {} failed final structure check",
                            session.ritual().id(),
                            session.center(),
                            level.dimension()
                    );
                    session.cancel(level, "Ритуал прерван: структура была нарушена.");
                } else {
                    RitualEffects.playCompletedEffect(level, session.center());
                    session.ritual().execute(level, session.center(), session.activator(level));
                }
            }

            if (session.isTerminal()) {
                toRemove.add(site);
            }
        }

        for (RitualSite site : toRemove) {
            ACTIVE_RITUALS.remove(site);
        }
    }

    private record RitualSite(ResourceKey<Level> dimension, BlockPos center) {
        private RitualSite {
            center = center.immutable();
        }
    }
}
