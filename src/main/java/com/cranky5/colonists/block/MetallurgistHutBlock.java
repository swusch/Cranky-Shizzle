package com.cranky5.colonists.block;

import com.cranky5.colonists.CrankyConstants;
import com.cranky5.colonists.registry.ModRegistry;
import com.minecolonies.api.blocks.AbstractBlockHut;
import com.minecolonies.api.colony.buildings.registry.BuildingEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The Metallurgist Hut block – a MineColonies hut that houses
 * a worker capable of crafting Mekanism alloys.
 */
public class MetallurgistHutBlock extends AbstractBlockHut<MetallurgistHutBlock> {

    public MetallurgistHutBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(2.0F, 6.0F)
                .requiresCorrectToolForDrops());
    }

    @NotNull
    @Override
    public String getHutName() {
        return CrankyConstants.METALLURGIST_HUT_NAME;
    }

    @NotNull
    @Override
    public String getDescriptionId() {
        return "block." + CrankyConstants.MOD_ID + "." + CrankyConstants.METALLURGIST_HUT_BLOCK;
    }

    @Override
    public BuildingEntry getBuildingEntry() {
        return ModRegistry.METALLURGIST_BUILDING.get();
    }

    /**
     * Custom block item that shows a tooltip explaining the hut's purpose.
     */
    public static class HutBlockItem extends net.minecraft.world.item.BlockItem {

        public HutBlockItem(MetallurgistHutBlock block) {
            super(block, new Item.Properties());
        }

        @Override
        public void appendHoverText(@NotNull ItemStack stack,
                                    @NotNull Item.TooltipContext ctx,
                                    @NotNull List<Component> tooltip,
                                    @NotNull TooltipFlag flag) {
            super.appendHoverText(stack, ctx, tooltip, flag);
            tooltip.add(Component.translatable("tooltip." + CrankyConstants.MOD_ID + ".metallurgist_hut")
                    .withStyle(net.minecraft.ChatFormatting.GRAY));
        }
    }
}
