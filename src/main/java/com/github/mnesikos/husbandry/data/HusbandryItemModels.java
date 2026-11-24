package com.github.mnesikos.husbandry.data;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HusbandryItemModels extends ItemModelProvider {
    public HusbandryItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Husbandry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        basicItem(HusbandryItems.MOOD_SCANNER.get());
        basicItem(HusbandryItems.FEED.get());
        basicItem(HusbandryItems.CANDIED_FEED.get());
        basicItem(HusbandryItems.MILK_PAIL.get());
        basicItem(HusbandryItems.MILK.get());
        basicItem(HusbandryItems.LARGE_MILK.get());
        basicItem(HusbandryItems.SHEEP_MILK.get());
        basicItem(HusbandryItems.LARGE_SHEEP_MILK.get());
        basicItem(HusbandryItems.GOAT_MILK.get());
        basicItem(HusbandryItems.LARGE_GOAT_MILK.get());
//        basicItem(HusbandryItems.LLAMA_MILK.get());
//        basicItem(HusbandryItems.LARGE_LLAMA_MILK.get());
        basicItem(HusbandryItems.AMETHYST_MILK.get());
        basicItem(HusbandryItems.LARGE_AMETHYST_MILK.get());
        basicItem(HusbandryItems.WARPED_MILK.get());
        basicItem(HusbandryItems.LARGE_WARPED_MILK.get());
        basicItem(HusbandryItems.LARGE_EGG.get());
    }
}
