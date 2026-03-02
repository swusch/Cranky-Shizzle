package com.cranky5.colonists;

import net.minecraft.resources.ResourceLocation;

/**
 * Central constants for the Cranky Colonists mod.
 * Prevents magic strings scattered across the codebase.
 */
public final class CrankyConstants {

    private CrankyConstants() { /* utility */ }

    // ── Mod identity ──────────────────────────────────────────
    public static final String MOD_ID = "crankycolonists";

    // ── Building / Job / Block names ──────────────────────────
    public static final String METALLURGIST_HUT_NAME = "metallurgist";
    public static final String METALLURGIST_HUT_BLOCK = "metallurgist_hut";
    public static final String METALLURGIST_JOB      = "metallurgist";

    // ── Mekanism integration ──────────────────────────────────
    public static final ResourceLocation METALLURGIC_INFUSER =
            ResourceLocation.fromNamespaceAndPath("mekanism", "metallurgic_infuser");

    // ── MineColonies registry keys ────────────────────────────
    public static final ResourceLocation MC_BUILDINGS_REGISTRY =
            ResourceLocation.fromNamespaceAndPath("minecolonies", "buildings");
    public static final ResourceLocation MC_JOBS_REGISTRY =
            ResourceLocation.fromNamespaceAndPath("minecolonies", "jobs");

    // ── Item identifiers ──────────────────────────────────────
    public static final String CREATIVE_ICON = "creative_icon";

    // ── Helpers ───────────────────────────────────────────────

    /**
     * Creates a {@link ResourceLocation} under this mod's namespace.
     */
    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
