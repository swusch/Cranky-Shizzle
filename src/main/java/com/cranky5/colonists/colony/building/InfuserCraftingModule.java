package com.cranky5.colonists.colony.building;

import com.cranky5.colonists.CrankyConstants;
import com.cranky5.colonists.config.CrankyConfig;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.api.crafting.IGenericRecipe;
import com.minecolonies.core.colony.buildings.modules.AbstractCraftingBuildingModule;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * Crafting module that filters recipes to only accept those using
 * the Mekanism Metallurgic Infuser as an intermediate crafting block.
 * <p>
 * This ensures the Metallurgist worker only works with recipes
 * that logically belong in the Infuser workflow (alloy production).
 */
public class InfuserCraftingModule extends AbstractCraftingBuildingModule.Crafting {

    public InfuserCraftingModule(@NotNull final JobEntry jobEntry) {
        super(jobEntry);
    }

    /**
     * Validates that a recipe uses the Metallurgic Infuser as its intermediate.
     * When the config option {@code requireInfuser} is disabled, all recipes pass.
     */
    @Override
    public boolean isRecipeCompatible(@NotNull final IGenericRecipe recipe) {
        // If config disables the requirement, accept any recipe assigned to this crafter
        if (!CrankyConfig.REQUIRE_INFUSER.get()) {
            return recipe.getIntermediate() != null && recipe.getIntermediate() != Blocks.AIR;
        }

        final var intermediate = recipe.getIntermediate();
        if (intermediate == null || intermediate == Blocks.AIR) {
            return false;
        }

        final ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(intermediate);
        return CrankyConstants.METALLURGIC_INFUSER.equals(blockId);
    }

    @NotNull
    @Override
    public String getId() {
        return "infuser_crafting";
    }
}
