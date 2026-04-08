package com.mortun.magicrituals.knowledge;

import com.mortun.magicrituals.research.ResearchEntryId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class KnowledgeRegistry {
    private static final Map<KnowledgeId, KnowledgeDefinition> REGISTRY = new HashMap<>();

    private KnowledgeRegistry() {}

    public static void register(KnowledgeDefinition def) {
        REGISTRY.put(def.id(), def);
    }

    public static Optional<KnowledgeDefinition> get(KnowledgeId id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }

    public static Map<KnowledgeId, KnowledgeDefinition> all() {
        return Map.copyOf(REGISTRY);
    }

    public static void init() {
        // Intentionally empty: triggers static initialization.
    }

    static {
        register(new KnowledgeDefinition(
                KnowledgeIds.FIRST_TRACE,
                "knowledge.magicrituals.first_trace.title",
                "knowledge.magicrituals.first_trace.desc",
                ResearchEntryId.of("first_trace")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.FRAGILE_ECHO,
                "knowledge.magicrituals.fragile_echo.title",
                "knowledge.magicrituals.fragile_echo.desc",
                ResearchEntryId.of("fragile_echo")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.TRACE_NEEDLE,
                "knowledge.magicrituals.trace_needle.title",
                "knowledge.magicrituals.trace_needle.desc",
                ResearchEntryId.of("trace_needle")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.TRACE_OF_FLOW,
                "knowledge.magicrituals.trace_of_flow.title",
                "knowledge.magicrituals.trace_of_flow.desc",
                ResearchEntryId.of("trace_of_flow")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.SETTLING,
                "knowledge.magicrituals.settling.title",
                "knowledge.magicrituals.settling.desc",
                ResearchEntryId.of("settling")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.RITUAL_CHALK,
                "knowledge.magicrituals.ritual_chalk.title",
                "knowledge.magicrituals.ritual_chalk.desc",
                ResearchEntryId.of("ritual_chalk")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.LISTENING_PATTERN,
                "knowledge.magicrituals.listening_pattern.title",
                "knowledge.magicrituals.listening_pattern.desc",
                ResearchEntryId.of("listening_pattern")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.RAINCALLER,
                "knowledge.magicrituals.raincaller.title",
                "knowledge.magicrituals.raincaller.desc",
                ResearchEntryId.of("raincaller")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.STORM_MARKS,
                "knowledge.magicrituals.storm_marks.title",
                "knowledge.magicrituals.storm_marks.desc",
                ResearchEntryId.of("storm_marks")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.TRACE_OF_HEAT,
                "knowledge.magicrituals.trace_of_heat.title",
                "knowledge.magicrituals.trace_of_heat.desc",
                ResearchEntryId.of("trace_of_heat")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.ASHEN_RESONANCE,
                "knowledge.magicrituals.ashen_resonance.title",
                "knowledge.magicrituals.ashen_resonance.desc",
                ResearchEntryId.of("ashen_resonance")
        ));

        register(new KnowledgeDefinition(
                KnowledgeIds.CLEAR_SKIES,
                "knowledge.magicrituals.clear_skies.title",
                "knowledge.magicrituals.clear_skies.desc",
                ResearchEntryId.of("clear_skies")
        ));
    }
}
