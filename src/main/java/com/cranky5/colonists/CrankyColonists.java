package com.cranky5.colonists;

import com.cranky5.colonists.config.CrankyConfig;
import com.cranky5.colonists.registry.ModRegistry;
import com.minecolonies.api.tileentities.MinecoloniesTileEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Main entry point for the Cranky Colonists mod.
 * <p>
 * This mod adds a Metallurgist hut to MineColonies whose colonist worker
 * crafts Mekanism alloys (Infused, Reinforced, Atomic) using the
 * Metallurgic Infuser as an intermediate crafting block.
 *
 * @author Cranky5
 */
@Mod(CrankyConstants.MOD_ID)
public class CrankyColonists {

    public static final Logger LOGGER = LogUtils.getLogger();

    public CrankyColonists(final IEventBus modBus, final ModContainer container) {

        // ── Register all content ──────────────────────────────
        ModRegistry.registerAll(modBus);

        // ── Lifecycle hooks ───────────────────────────────────
        modBus.addListener(this::onCommonSetup);
        NeoForge.EVENT_BUS.register(this);

        // ── Config ────────────────────────────────────────────
        container.registerConfig(ModConfig.Type.COMMON, CrankyConfig.SPEC);

        LOGGER.info("Cranky Colonists initialized – Metallurgist Hut ready");
    }

    /**
     * Injects our custom hut block into the MineColonies
     * {@code colonybuilding} BlockEntityType so it is recognised as a valid
     * colony building.  This requires the access transformer that makes
     * {@code BlockEntityType.validBlocks} public and non-final.
     */
    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            final BlockEntityType<?> buildingType = MinecoloniesTileEntities.BUILDING.get();
            final Set<Block> expanded = new HashSet<>(buildingType.validBlocks);
            expanded.add(ModRegistry.METALLURGIST_HUT_BLOCK.get());
            buildingType.validBlocks = expanded;

            LOGGER.info("Injected {} into MineColonies building block-entity type",
                    ModRegistry.METALLURGIST_HUT_BLOCK.getId());
        });
    }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) {
        if (CrankyConfig.DEBUG_LOGGING.get()) {
            LOGGER.info("[CrankyColonists] Server starting – mod is active");
        }
    }
}
