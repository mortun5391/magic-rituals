package com.mortun.magicrituals.knowledge;

import java.util.HashSet;
import java.util.Set;

/**
 * PlayerKnowledge -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-08
 */
public class PlayerKnowledge {
    private final Set<KnowledgeId> knowledge = new HashSet<>();

    public boolean has(KnowledgeId id) {
        return knowledge.contains(id);
    }

    public void grant(KnowledgeId id) {
        knowledge.add(id);
    }

    public Set<KnowledgeId> getAll() {
        return Set.copyOf(knowledge);
    }
}
