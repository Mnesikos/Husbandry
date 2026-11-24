package com.github.mnesikos.husbandry.data;

import com.github.mnesikos.husbandry.block.HusbandryBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class HusbandryLootTables extends BlockLootSubProvider {
    public HusbandryLootTables(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    @Override
    protected void generate() {
        dropSelf(HusbandryBlocks.BARN_STATION.get());
        dropSelf(HusbandryBlocks.COOP_STATION.get());
        dropSelf(HusbandryBlocks.TROUGH.get());
        dropSelf(HusbandryBlocks.DELUXE_TROUGH.get());
        dropSelf(HusbandryBlocks.INCUBATOR.get());
        dropSelf(HusbandryBlocks.AUTO_GRABBER.get());
        dropSelf(HusbandryBlocks.AUTO_PETTER.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return HusbandryBlocks.REGISTRAR.getEntries().stream()
                .map(e -> (Block) e.value())
                .toList();
    }
}
