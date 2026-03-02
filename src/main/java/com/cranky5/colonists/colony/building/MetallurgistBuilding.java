package com.cranky5.colonists.colony.building;

import com.cranky5.colonists.CrankyConstants;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.core.colony.buildings.AbstractBuilding;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * Server-side building data for the Metallurgist Hut.
 * <p>
 * Holds worker assignments, recipe lists, and upgrade state.
 * The actual crafting module is {@link InfuserCraftingModule}.
 */
public class MetallurgistBuilding extends AbstractBuilding {

    /** Maximum upgrade level for this building. */
    private static final int MAX_LEVEL = 5;

    public MetallurgistBuilding(@NotNull final IColony colony, @NotNull final BlockPos pos) {
        super(colony, pos);
    }

    @NotNull
    @Override
    public String getSchematicName() {
        return CrankyConstants.METALLURGIST_HUT_NAME + "_hut";
    }

    @Override
    public int getMaxBuildingLevel() {
        return MAX_LEVEL;
    }
}
