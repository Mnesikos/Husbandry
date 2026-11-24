package com.github.mnesikos.husbandry.data;

import com.github.mnesikos.husbandry.item.AnimalFeed;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class HusbandryDataMapProvider extends DataMapProvider {
    public HusbandryDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(AnimalFeed.ANIMAL_FEED)
                .add(HusbandryItems.FEED, new AnimalFeed(10), false)
                .add(HusbandryItems.CANDIED_FEED, new AnimalFeed(100, 100), false);
    }
}
