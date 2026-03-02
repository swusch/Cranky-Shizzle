package com.cranky5.colonists.colony.building;

import com.minecolonies.api.colony.IColonyView;
import com.minecolonies.core.colony.buildings.views.AbstractBuildingView;
import net.minecraft.core.BlockPos;

/**
 * Client-side view of the Metallurgist Hut building.
 * <p>
 * Currently delegates to the default MineColonies building GUI.
 * Extend this class to add custom tabs or controls in the future.
 */
public class MetallurgistBuildingView extends AbstractBuildingView {

    public MetallurgistBuildingView(final IColonyView colonyView, final BlockPos pos) {
        super(colonyView, pos);
    }
}
