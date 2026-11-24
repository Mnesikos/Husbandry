package com.github.mnesikos.husbandry;

import com.github.mnesikos.husbandry.block.HusbandryBlocks;
import com.github.mnesikos.husbandry.data.*;
import com.github.mnesikos.husbandry.dataattachments.HusbandryAttachmentTypes;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import com.github.mnesikos.husbandry.resources.HusbandryDefinitions;
import com.mojang.logging.LogUtils;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mod(Husbandry.MOD_ID)
public class Husbandry {
    public static final String MOD_ID = "husbandry";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HUSBANDRY_TAB = CREATIVE_MODE_TABS.register(MOD_ID + ".tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID))
            .icon(() -> HusbandryItems.FEED.get().getDefaultInstance())
            .displayItems((parameters, output) -> HusbandryItems.REGISTRAR.getEntries().forEach(item -> output.accept(item.get())))
            .build());

    public Husbandry(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        HusbandryAttachmentTypes.REGISTRAR.register(modEventBus);
        HusbandryBlocks.REGISTRAR.register(modEventBus);
        HusbandryItems.REGISTRAR.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

//        dataGenerator.addProvider(event.includeClient(), new HusbandryBlockModels(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new HusbandryBlockStates(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new HusbandryItemModels(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new HusbandryLang(packOutput));

        HusbandryTagsProvider.BlockTags blockTagsProvider = new HusbandryTagsProvider.BlockTags(packOutput, event.getLookupProvider(), existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), blockTagsProvider);
        dataGenerator.addProvider(event.includeServer(), new HusbandryTagsProvider.ItemTags(packOutput, event.getLookupProvider(), blockTagsProvider, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new HusbandryTagsProvider.EntityTypeTags(packOutput, event.getLookupProvider(), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(HusbandryLootTables::new, LootContextParamSets.BLOCK)), event.getLookupProvider()));
        dataGenerator.addProvider(event.includeServer(), new HusbandryRecipes(packOutput, event.getLookupProvider()));


        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
                .add(HusbandryDefinitions.KEY, HusbandryDefinitions::bootstrap);
        dataGenerator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), registrySetBuilder, Set.of("minecraft")));
        dataGenerator.addProvider(event.includeServer(), new HusbandryDataMapProvider(packOutput, event.getLookupProvider()));
    }
}
