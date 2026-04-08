package com.mortun.magicrituals.knowledge;

import com.mortun.magicrituals.research.ResearchEntryId;

/**
 * KnowledgeDefinition -
 *
 * @author 1
 * @version 1.0
 * @since 2026-04-08
 */
public record KnowledgeDefinition(KnowledgeId id, String titleKey, String descriptionKey, ResearchEntryId unlocksFrom) {
}
