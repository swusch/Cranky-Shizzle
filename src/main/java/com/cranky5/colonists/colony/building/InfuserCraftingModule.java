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
 * Crafting module that accepts recipes using the Mekanism Metallurgic
 * Infuser as an intermediate crafting block.
 * <p>
 * Extends {@link AbstractCraftingBuildingModule.Custom} because these
 * recipes cannot be taught through a vanilla crafting grid — they are
 * loaded exclusively from datapack {@code crafterrecipes} JSON files.
 * The {@code Custom} policy class correctly returns an empty set of
 * supported crafting types, which hides the "teach recipe" button and
 * ensures only datapack-loaded recipes appear.
 * <p>
 * The {@code getCustomRecipeKey()} (inherited) produces
 * {@code "<jobPath>_<getId()>"} = {@code "metallurgist_infuser_crafting"},
 * which must match the {@code "crafter"} field in each crafterrecipe JSON.
 */
public class InfuserCraftingModule extends AbstractCraftingBuildingModule.Custom {

    public InfuserCraftingModule(@NotNull final JobEntry jobEntry) {
        super(jobEntry);
    }

    /**
     * Validates that a recipe uses the Metallurgic Infuser as its intermediate.
     * <p>
     * Although the {@code Custom} base class returns {@code false} by default
     * (preventing manual teaching), this override is kept so that JEI
     * integration and recipe analysis can correctly identify compatible recipes.
     * Datapack-loaded recipes bypass this check via {@code addRecipeToList()}.
     */
    @Override
    public boolean isRecipeCompatible(@NotNull final IGenericRecipe recipe) {
        final var intermediate = recipe.getIntermediate();
        if (intermediate == null || intermediate == Blocks.AIR) {
            return false;
        }

        // If config disables the infuser requirement, accept any non-AIR intermediate
        if (!CrankyConfig.REQUIRE_INFUSER.get()) {
            return true;
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
