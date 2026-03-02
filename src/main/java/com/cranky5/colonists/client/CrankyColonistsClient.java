package com.cranky5.colonists.client;

import com.cranky5.colonists.CrankyConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * Client-side entry point for Cranky Colonists.
 * Registers the in-game config screen and any future client-only logic.
 */
@Mod(value = CrankyConstants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CrankyConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CrankyColonistsClient {

    public CrankyColonistsClient(final ModContainer container) {
        // Register NeoForge's built-in config GUI
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(final FMLClientSetupEvent event) {
        // Future: register citizen model layers, custom screens, etc.
    }
}
