package com.cranky5.colonists.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Mod configuration for Cranky Colonists.
 * <p>
 * Uses sectioned layout with push/pop for clean organisation in the config GUI.
 */
public final class CrankyConfig {

    private CrankyConfig() { /* utility */ }

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // ── Metallurgist Hut section ──────────────────────────────

    static {
        BUILDER.comment("Settings for the Metallurgist Hut and its worker.").push("metallurgist");
    }

    /** Master toggle for the Metallurgist hut. */
    public static final ModConfigSpec.BooleanValue ENABLE_METALLURGIST =
            BUILDER.comment("Enable or disable the Metallurgist Hut building entirely.")
                   .define("enabled", true);

    /** Maximum concurrent recipes the worker can handle (scales with building level). */
    public static final ModConfigSpec.IntValue MAX_RECIPES =
            BUILDER.comment("Maximum number of concurrent crafting recipes a Metallurgist can handle.",
                            "Actual cap is min(this value, building level).")
                   .defineInRange("maxRecipes", 2, 1, 16);

    /** Whether the Metallurgic Infuser is strictly required as intermediate. */
    public static final ModConfigSpec.BooleanValue REQUIRE_INFUSER =
            BUILDER.comment("If true, only recipes that specify the Metallurgic Infuser as the",
                            "intermediate block will be accepted by the Metallurgist.")
                   .define("requireInfuser", true);

    /** Whether research must be completed before the hut can be built. */
    public static final ModConfigSpec.BooleanValue REQUIRE_RESEARCH =
            BUILDER.comment("If true, the Metallurgist Hut requires a research to be completed",
                            "before it can be placed.")
                   .define("requireResearch", true);

    static {
        BUILDER.pop();
    }

    // ── General section ──────────────────────────────────────

    static {
        BUILDER.comment("General mod settings.").push("general");
    }

    /** Enable verbose debug logging. */
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING =
            BUILDER.comment("Enable verbose debug logging for troubleshooting.")
                   .define("debugLogging", false);

    static {
        BUILDER.pop();
    }

    /** The built config specification. */
    public static final ModConfigSpec SPEC = BUILDER.build();
}
