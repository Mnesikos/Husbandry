package com.github.mnesikos.husbandry.item;

import com.github.mnesikos.husbandry.Husbandry;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HusbandryItems {
    public static final DeferredRegister.Items REGISTRAR = DeferredRegister.createItems(Husbandry.MOD_ID);

    public static final DeferredItem<Item> MOOD_SCANNER = REGISTRAR.register("mood_scanner", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FEED = REGISTRAR.register("feed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CANDIED_FEED = REGISTRAR.register("candied_feed", () -> new Item(new Item.Properties()));

    public static final DeferredItem<MilkPailItem> MILK_PAIL = REGISTRAR.register("milk_pail", () -> new MilkPailItem(new Item.Properties()));
    public static final DeferredItem<Item> MILK = REGISTRAR.register("milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.MILK)));
    public static final DeferredItem<Item> LARGE_MILK = REGISTRAR.register("large_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LARGE_MILK)));
    public static final DeferredItem<Item> SHEEP_MILK = REGISTRAR.register("sheep_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.SHEEP_MILK)));
    public static final DeferredItem<Item> LARGE_SHEEP_MILK = REGISTRAR.register("large_sheep_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LARGE_SHEEP_MILK)));
    public static final DeferredItem<Item> GOAT_MILK = REGISTRAR.register("goat_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.GOAT_MILK)));
    public static final DeferredItem<Item> LARGE_GOAT_MILK = REGISTRAR.register("large_goat_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LARGE_GOAT_MILK)));
    public static final DeferredItem<Item> LLAMA_MILK = REGISTRAR.register("llama_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LLAMA_MILK)));
    public static final DeferredItem<Item> LARGE_LLAMA_MILK = REGISTRAR.register("large_llama_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LARGE_LLAMA_MILK)));
    public static final DeferredItem<Item> AMETHYST_MILK = REGISTRAR.register("amethyst_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.AMETHYST_MILK)));
    public static final DeferredItem<Item> LARGE_AMETHYST_MILK = REGISTRAR.register("large_amethyst_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LARGE_AMETHYST_MILK)));
    public static final DeferredItem<Item> WARPED_MILK = REGISTRAR.register("warped_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.WARPED_MILK)));
    public static final DeferredItem<Item> LARGE_WARPED_MILK = REGISTRAR.register("large_warped_milk", () -> new MilkItem(new Item.Properties().food(HusbandryFoods.LARGE_WARPED_MILK)));

    public static final DeferredItem<Item> LARGE_EGG = REGISTRAR.register("large_egg", () -> new Item(new Item.Properties()));
}
