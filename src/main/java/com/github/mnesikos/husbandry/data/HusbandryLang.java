package com.github.mnesikos.husbandry.data;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.block.HusbandryBlocks;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class HusbandryLang extends LanguageProvider {
    public HusbandryLang(PackOutput output) {
        super(output, Husbandry.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.husbandry", "Untitled Husbandry Mod");

        add("husbandry.peckish", "%s could use something to eat...");
        add("husbandry.hungry", "%s went too long without food...");
        add("husbandry.crowded", "%s feels crowded and unhappy...");
        add("husbandry.action.young", "%s is too young to produce milk!");
        add("husbandry.action.hungry", "%s is too hungry to produce milk!");
        add("husbandry.action.unhappy", "%s is too unhappy to produce milk!");
        add("husbandry.action.cooldown", "%s needs rest...");

        addItem(HusbandryItems.MOOD_SCANNER, "Mood Scanner");
        addItem(HusbandryItems.FEED, "Feed");
        addItem(HusbandryItems.CANDIED_FEED, "Candied Feed");
        addItem(HusbandryItems.MILK_PAIL, "Milk Pail");
        addItem(HusbandryItems.MILK, "Milk");
        addItem(HusbandryItems.LARGE_MILK, "Large Milk");
        addItem(HusbandryItems.SHEEP_MILK, "Sheep Milk");
        addItem(HusbandryItems.LARGE_SHEEP_MILK, "Large Sheep Milk");
        addItem(HusbandryItems.GOAT_MILK, "Goat Milk");
        addItem(HusbandryItems.LARGE_GOAT_MILK, "Large Goat Milk");
        addItem(HusbandryItems.LLAMA_MILK, "Llama Milk");
        addItem(HusbandryItems.LARGE_LLAMA_MILK, "Large Llama Milk");
        addItem(HusbandryItems.AMETHYST_MILK, "Amethyst Milk");
        addItem(HusbandryItems.LARGE_AMETHYST_MILK, "Large Amethyst Milk");
        addItem(HusbandryItems.WARPED_MILK, "Warped Milk");
        addItem(HusbandryItems.LARGE_WARPED_MILK, "Large Warped Milk");
        addItem(HusbandryItems.LARGE_EGG, "Large Egg");

        addBlock(HusbandryBlocks.BARN_STATION, "Barn Station");
        addBlock(HusbandryBlocks.COOP_STATION, "Coop Station");
        addBlock(HusbandryBlocks.TROUGH, "Trough");
        addBlock(HusbandryBlocks.DELUXE_TROUGH, "Deluxe Trough");
        addBlock(HusbandryBlocks.INCUBATOR, "Incubator");
        addBlock(HusbandryBlocks.AUTO_GRABBER, "Auto Grabber");
        addBlock(HusbandryBlocks.AUTO_PETTER, "Auto Petter");
    }
}
