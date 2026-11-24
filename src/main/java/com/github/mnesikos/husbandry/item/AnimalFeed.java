package com.github.mnesikos.husbandry.item;

import com.github.mnesikos.husbandry.Husbandry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public record AnimalFeed(int handFed, int troughFed) {
    public AnimalFeed(int handFed) {
        this(handFed, 0);
    }

    public static final Codec<AnimalFeed> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("hand_fed").forGetter(AnimalFeed::handFed),
                    Codec.INT.optionalFieldOf("trough_fed", 0).forGetter(AnimalFeed::troughFed)
            ).apply(instance, AnimalFeed::new)
    );

    public static final DataMapType<Item, AnimalFeed> ANIMAL_FEED = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "animal_feed"),
            Registries.ITEM,
            AnimalFeed.CODEC
    ).build();
}
