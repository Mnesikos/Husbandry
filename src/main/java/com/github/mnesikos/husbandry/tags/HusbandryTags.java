package com.github.mnesikos.husbandry.tags;

import com.github.mnesikos.husbandry.Husbandry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class HusbandryTags {
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> PET_ANIMAL = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "pet_animal"));
        public static final TagKey<EntityType<?>> HUSBANDRY_ANIMAL = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "husbandry_animal"));
        public static final TagKey<EntityType<?>> MILKABLE = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "husbandry_animal/milkable"));
        public static final TagKey<EntityType<?>> COOPMASTER_BIRD = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "husbandry_animal/coopmaster_bird"));
        public static final TagKey<EntityType<?>> TIER_TWO = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "husbandry_animal/tier_two"));
        public static final TagKey<EntityType<?>> TIER_THREE = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "husbandry_animal/tier_three"));
        public static final TagKey<EntityType<?>> INFERTILE = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "infertile"));
        public static final TagKey<EntityType<?>> COLD = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "husbandry_animal/cold"));
    }

    public static class Blocks {
        public static final TagKey<Block> COLD = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "cold"));
    }
}
