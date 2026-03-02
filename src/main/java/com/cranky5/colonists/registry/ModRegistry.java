package com.cranky5.colonists.registry;

import com.cranky5.colonists.CrankyConstants;
import com.cranky5.colonists.block.MetallurgistHutBlock;
import com.cranky5.colonists.colony.building.InfuserCraftingModule;
import com.cranky5.colonists.colony.building.MetallurgistBuilding;
import com.cranky5.colonists.colony.building.MetallurgistBuildingView;
import com.minecolonies.api.colony.buildings.modules.IBuildingModule;
import com.minecolonies.api.colony.buildings.registry.BuildingEntry;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.api.entity.citizen.Skill;
import com.minecolonies.core.colony.buildings.modules.AbstractCraftingBuildingModule;
import com.minecolonies.core.colony.buildings.modules.CraftingWorkerBuildingModule;
import com.minecolonies.core.colony.buildings.modules.SettingsModule;
import com.minecolonies.core.colony.buildings.modules.settings.CrafterRecipeSetting;
import com.minecolonies.core.colony.buildings.moduleviews.CraftingModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.CrafterRequestTaskModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.RequestTaskModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.SettingsModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.WorkerBuildingModuleView;
import com.minecolonies.core.colony.jobs.JobMechanic;
import com.minecolonies.core.colony.jobs.views.CrafterJobView;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Unified registry for all mod content.
 * <p>
 * Keeps all {@link DeferredRegister}s and their entries in a single location
 * so they can be discovered, audited, and registered from one call-site.
 */
public final class ModRegistry {

    private ModRegistry() { /* utility */ }

    // ═══════════════════════════════════════════════════════════
    //  Deferred Registers
    // ═══════════════════════════════════════════════════════════

    public static final DeferredRegister<Block>          BLOCKS   = DeferredRegister.create(Registries.BLOCK, CrankyConstants.MOD_ID);
    public static final DeferredRegister<Item>           ITEMS    = DeferredRegister.create(Registries.ITEM, CrankyConstants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS    = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrankyConstants.MOD_ID);
    public static final DeferredRegister<BuildingEntry>  BUILDINGS = DeferredRegister.create(CrankyConstants.MC_BUILDINGS_REGISTRY, CrankyConstants.MOD_ID);
    public static final DeferredRegister<JobEntry>       JOBS     = DeferredRegister.create(CrankyConstants.MC_JOBS_REGISTRY, CrankyConstants.MOD_ID);

    // ═══════════════════════════════════════════════════════════
    //  Blocks
    // ═══════════════════════════════════════════════════════════

    public static final DeferredHolder<Block, MetallurgistHutBlock> METALLURGIST_HUT_BLOCK =
            BLOCKS.register(CrankyConstants.METALLURGIST_HUT_BLOCK, MetallurgistHutBlock::new);

    // ═══════════════════════════════════════════════════════════
    //  Items
    // ═══════════════════════════════════════════════════════════

    /** The hut's block item (with tooltip). */
    public static final DeferredHolder<Item, MetallurgistHutBlock.HutBlockItem> METALLURGIST_HUT_ITEM =
            ITEMS.register(CrankyConstants.METALLURGIST_HUT_BLOCK,
                    () -> new MetallurgistHutBlock.HutBlockItem(METALLURGIST_HUT_BLOCK.get()));

    /** Decorative icon used on the creative tab. */
    public static final DeferredHolder<Item, Item> CREATIVE_ICON =
            ITEMS.register(CrankyConstants.CREATIVE_ICON,
                    () -> new Item(new Item.Properties()));

    // ═══════════════════════════════════════════════════════════
    //  Jobs
    // ═══════════════════════════════════════════════════════════

    /**
     * Metallurgist job entry.
     * Uses the MineColonies Mechanic AI as a stand-in until a dedicated
     * Metallurgist AI is implemented (the Mechanic AI already handles
     * recipe-based crafting at intermediate blocks).
     */
    public static final DeferredHolder<JobEntry, JobEntry> METALLURGIST_JOB =
            JOBS.register(CrankyConstants.METALLURGIST_JOB, () ->
                    new JobEntry.Builder()
                            .setJobProducer(JobMechanic::new)
                            .setJobViewProducer(() -> CrafterJobView::new)
                            .setRegistryName(CrankyConstants.rl(CrankyConstants.METALLURGIST_JOB))
                            .createJobEntry());

    // ═══════════════════════════════════════════════════════════
    //  Building Module Producers
    // ═══════════════════════════════════════════════════════════

    /**
     * Worker assignment module – uses {@link CraftingWorkerBuildingModule}
     * (not plain {@code WorkerBuildingModule}) so the MineColonies request
     * system can route crafting tasks to this worker.
     * 1 worker, Knowledge primary / Agility secondary.
     */
    private static final BuildingEntry.ModuleProducer<CraftingWorkerBuildingModule, WorkerBuildingModuleView> WORKER_MODULE =
            new BuildingEntry.ModuleProducer<>(
                    "metallurgist_worker",
                    () -> new CraftingWorkerBuildingModule(
                            METALLURGIST_JOB.get(),
                            Skill.Knowledge,
                            Skill.Agility,
                            false,
                            b -> 1),
                    () -> WorkerBuildingModuleView::new);

    /** Crafting module that filters for Metallurgic Infuser recipes. */
    private static final BuildingEntry.ModuleProducer<InfuserCraftingModule, CraftingModuleView> CRAFTING_MODULE =
            new BuildingEntry.ModuleProducer<>(
                    "infuser_crafting",
                    () -> new InfuserCraftingModule(METALLURGIST_JOB.get()),
                    () -> CraftingModuleView::new);

    /** Settings module – provides the recipe-priority/max-stock toggle (requires Warehouse Master research). */
    private static final BuildingEntry.ModuleProducer<SettingsModule, SettingsModuleView> SETTINGS_MODULE =
            new BuildingEntry.ModuleProducer<>(
                    "metallurgist_settings",
                    () -> new SettingsModule()
                            .with(AbstractCraftingBuildingModule.RECIPE_MODE, new CrafterRecipeSetting()),
                    () -> SettingsModuleView::new);

    /** Task-view module – shows pending crafting requests in the hut GUI. */
    private static final BuildingEntry.ModuleProducer<IBuildingModule, RequestTaskModuleView> TASK_VIEW_MODULE =
            new BuildingEntry.ModuleProducer<>(
                    "metallurgist_task_view",
                    null,
                    () -> CrafterRequestTaskModuleView::new);

    // ═══════════════════════════════════════════════════════════
    //  Buildings
    // ═══════════════════════════════════════════════════════════

    public static final DeferredHolder<BuildingEntry, BuildingEntry> METALLURGIST_BUILDING =
            BUILDINGS.register(CrankyConstants.METALLURGIST_HUT_BLOCK, () ->
                    new BuildingEntry.Builder()
                            .setBuildingBlock(METALLURGIST_HUT_BLOCK.get())
                            .setBuildingProducer(MetallurgistBuilding::new)
                            .setBuildingViewProducer(() -> MetallurgistBuildingView::new)
                            .setRegistryName(CrankyConstants.rl(CrankyConstants.METALLURGIST_HUT_BLOCK))
                            .addBuildingModuleProducer(WORKER_MODULE)
                            .addBuildingModuleProducer(CRAFTING_MODULE)
                            .addBuildingModuleProducer(SETTINGS_MODULE)
                            .addBuildingModuleProducer(TASK_VIEW_MODULE)
                            .createBuildingEntry());

    // ═══════════════════════════════════════════════════════════
    //  Creative Tab
    // ═══════════════════════════════════════════════════════════

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            TABS.register("cranky_tab", () ->
                    CreativeModeTab.builder()
                            .title(net.minecraft.network.chat.Component.translatable("itemGroup." + CrankyConstants.MOD_ID))
                            .icon(() -> CREATIVE_ICON.get().getDefaultInstance())
                            .displayItems((params, output) -> {
                                output.accept(METALLURGIST_HUT_ITEM.get());
                            })
                            .build());

    // ═══════════════════════════════════════════════════════════
    //  Bulk Registration
    // ═══════════════════════════════════════════════════════════

    /**
     * Registers every {@link DeferredRegister} on the given mod event bus.
     * Call this once from the mod constructor.
     */
    public static void registerAll(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TABS.register(bus);
        BUILDINGS.register(bus);
        JOBS.register(bus);
    }
}
