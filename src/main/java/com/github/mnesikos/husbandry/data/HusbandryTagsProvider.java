package com.github.mnesikos.husbandry.data;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.tags.HusbandryTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HusbandryTagsProvider {
    public static class BlockTags extends BlockTagsProvider {
        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, Husbandry.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }

    public static class ItemTags extends ItemTagsProvider {
        public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags.contentsGetter(), Husbandry.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }

    public static class EntityTypeTags extends EntityTypeTagsProvider {
        public EntityTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, provider, Husbandry.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL).addTag(HusbandryTags.EntityTypes.MILKABLE);
            tag(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL).addTag(HusbandryTags.EntityTypes.COOPMASTER_BIRD);
            tag(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL).addTag(HusbandryTags.EntityTypes.TIER_TWO);
            tag(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL).addTag(HusbandryTags.EntityTypes.TIER_THREE);
            tag(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL).add(
                    EntityType.SNIFFER, EntityType.FROG, EntityType.BAT
            );
            tag(HusbandryTags.EntityTypes.TIER_TWO).add(
                    EntityType.PIG, EntityType.PANDA, EntityType.MOOSHROOM, EntityType.RABBIT, EntityType.TURTLE
            );
            tag(HusbandryTags.EntityTypes.TIER_THREE).add(
                    EntityType.GOAT
            );
            tag(HusbandryTags.EntityTypes.MILKABLE).add(
                    EntityType.COW, EntityType.GOAT, EntityType.SHEEP, EntityType.MOOSHROOM, EntityType.SQUID, EntityType.GLOW_SQUID
            );
            tag(HusbandryTags.EntityTypes.COOPMASTER_BIRD).add(
                    EntityType.CHICKEN
            );
            tag(HusbandryTags.EntityTypes.PET_ANIMAL).add(
                    EntityType.WOLF, EntityType.CAT, EntityType.ALLAY, EntityType.HORSE, EntityType.POLAR_BEAR, EntityType.AXOLOTL
            );
        }
    }
}
