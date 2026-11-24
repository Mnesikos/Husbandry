package com.github.mnesikos.husbandry.resources;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Optional;

public record HusbandryDefinitions(List<Forage> forages, Milk milk) {
    public static final ResourceKey<Registry<HusbandryDefinitions>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Husbandry.MOD_ID, "definitions"));
    public static final HusbandryDefinitions EMPTY = new HusbandryDefinitions(List.of(), Milk.EMPTY);

    public static final Codec<HusbandryDefinitions> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Forage.CODEC.listOf().optionalFieldOf("forage", List.of()).forGetter(HusbandryDefinitions::forages),
                    Milk.CODEC.optionalFieldOf("milk", Milk.EMPTY).forGetter(HusbandryDefinitions::milk)
            ).apply(instance, HusbandryDefinitions::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, HusbandryDefinitions> STREAM_CODEC = StreamCodec.composite(
            Forage.STREAM_CODEC.apply(ByteBufCodecs.list()), HusbandryDefinitions::forages,
            Milk.STREAM_CODEC, HusbandryDefinitions::milk,
            HusbandryDefinitions::new
    );

    public static HusbandryDefinitions get(Entity entity) {
        Optional<HusbandryDefinitions> definitions = entity.level().registryAccess().registryOrThrow(KEY).getOptional(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
        return definitions.orElse(EMPTY);
    }

    public ItemStack getRandomForage(RandomSource random, int hearts) {
        for (Forage forage : forages) {
            if (hearts >= forage.heartsRequired && random.nextFloat() <= forage.chance) {
                List<ItemStack> items = forage.items();
                ItemStack stack = items.get(random.nextInt(items.size()));
                if (!stack.isEmpty()) return stack.copy();
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack getMilk(int hearts) {
        boolean large = hearts > 5 /*&& Math.random() < (mood + hearts * 10) / 256*/;
        ItemStack stack = large ? milk.largeMilk : milk.smallMilk;
        stack.setCount((/*stages.has("shepherd") ? 2 :*/ 1) * /*crackerBonus **/(/*plushieDoubleDrops ? 2 :*/ 1));
        return stack.copy();
    }

    public static void bootstrap(BootstrapContext<HusbandryDefinitions> context) {
        registerForaging(context, EntityType.BAT,
                Forage.singleItemForageList(Items.APPLE, 3, 1.0F, 2, true)
        );
        registerForaging(context, EntityType.CHICKEN,
                Forage.singleItemForageList(HusbandryItems.LARGE_EGG.get(), 1.0F, 4, true)
        );
        registerMilking(context, EntityType.COW,
                new Milk(HusbandryItems.MILK.toStack(), HusbandryItems.LARGE_MILK.toStack(), 1)
        );
        registerForaging(context, EntityType.FROG,
                Forage.singleForageList(List.of(Items.OCHRE_FROGLIGHT, Items.VERDANT_FROGLIGHT, Items.PEARLESCENT_FROGLIGHT), 8, 0.25F, 5, false)
        );
        registerMilking(context, EntityType.GLOW_SQUID,
                new Milk(Items.GLOW_INK_SAC.getDefaultInstance(), Items.GLOW_INK_SAC.getDefaultInstance(), 1)
        );
        registerMilking(context, EntityType.GOAT,
                new Milk(HusbandryItems.GOAT_MILK.toStack(), HusbandryItems.LARGE_GOAT_MILK.toStack(), 1)
        );
        register(context, EntityType.MOOSHROOM,
                Forage.singleItemForageList(Items.BROWN_MUSHROOM, 0.3F, 4, true),
                new Milk(HusbandryItems.MILK.toStack(), HusbandryItems.LARGE_MILK.toStack(), 1)
        );
        registerForaging(context, EntityType.PIG,
                Forage.singleItemForageList(Items.BROWN_MUSHROOM, 0.3F, 4, true)
        );
        registerForaging(context, EntityType.RABBIT, List.of(
                Forage.singleItemForage(Items.WHITE_WOOL, 1, 0.33F, 3, true),
                Forage.singleItemForage(Items.RABBIT_FOOT, 1, 0.16F, 5, true)
        ));
        register(context, EntityType.SHEEP,
                Forage.singleItemForageList(Items.WHITE_WOOL, 0.5F, 4, true),
                new Milk(HusbandryItems.SHEEP_MILK.toStack(), HusbandryItems.LARGE_SHEEP_MILK.toStack(), 2)
        );
        registerForaging(context, EntityType.SNIFFER,
                Forage.singleItemForageList(Items.SNIFFER_EGG, 0.25F, 5, true)
        );
        registerMilking(context, EntityType.SQUID,
                new Milk(Items.INK_SAC.getDefaultInstance(), Items.INK_SAC.getDefaultInstance(), 1)
        );
        registerForaging(context, EntityType.TURTLE,
                Forage.singleItemForageList(Items.TURTLE_EGG, 2, 0.25F, 5, true)
        );
    }

    private static ResourceKey<HusbandryDefinitions> keyByEntityType(EntityType<?> entityType) {
        return ResourceKey.create(KEY, BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
    }

    public static void registerForaging(BootstrapContext<HusbandryDefinitions> context, EntityType<?> entityType, List<Forage> forages) {
        register(context, entityType, forages, Milk.EMPTY);
    }

    public static void registerMilking(BootstrapContext<HusbandryDefinitions> context, EntityType<?> entityType, Milk milk) {
        register(context, entityType, List.of(), milk);
    }

    public static void register(BootstrapContext<HusbandryDefinitions> context, EntityType<?> entityType, List<Forage> forages, Milk milk) {
        context.register(keyByEntityType(entityType), new HusbandryDefinitions(forages, milk));
    }

    public record Forage(List<ItemStack> items, float chance, int heartsRequired, boolean quality) {
        public static final Codec<Forage> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ItemStack.CODEC.listOf().fieldOf("item").forGetter(Forage::items),
                        Codec.FLOAT.fieldOf("chance").forGetter(Forage::chance),
                        Codec.INT.fieldOf("hearts_required").forGetter(Forage::heartsRequired),
                        Codec.BOOL.optionalFieldOf("quality", false).forGetter(Forage::quality)
                ).apply(instance, Forage::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, Forage> STREAM_CODEC = StreamCodec.composite(
                ItemStack.LIST_STREAM_CODEC, Forage::items,
                ByteBufCodecs.FLOAT, Forage::chance,
                ByteBufCodecs.INT, Forage::heartsRequired,
                ByteBufCodecs.BOOL, Forage::quality,
                Forage::new
        );

        public static List<Forage> singleItemForageList(Item item, int count, float chance, int heartsRequired, boolean quality) {
            return List.of(singleItemForage(item, count, chance, heartsRequired, quality));
        }

        public static List<Forage> singleItemForageList(Item item, float chance, int heartsRequired, boolean quality) {
            return List.of(singleItemForage(item, 1, chance, heartsRequired, quality));
        }

        public static List<Forage> singleForageList(List<Item> items, int count, float chance, int heartsRequired, boolean quality) {
            List<ItemStack> itemStacks = items.stream().map(item -> item.getDefaultInstance().copyWithCount(count)).toList();
            return List.of(new Forage(itemStacks, chance, heartsRequired, quality));
        }

        public static Forage singleItemForage(Item item, int count, float chance, int heartsRequired, boolean quality) {
            return new Forage(List.of(item.getDefaultInstance().copyWithCount(count)), chance, heartsRequired, quality);
        }
    }

    public record Milk(ItemStack smallMilk, ItemStack largeMilk, int cooldown) {
        public static final Milk EMPTY = new Milk(ItemStack.EMPTY, ItemStack.EMPTY, 0);

        public static final Codec<Milk> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ItemStack.SIMPLE_ITEM_CODEC.fieldOf("small").forGetter(Milk::smallMilk),
                        ItemStack.SIMPLE_ITEM_CODEC.fieldOf("large").forGetter(Milk::largeMilk),
                        Codec.INT.optionalFieldOf("cooldown", 1).forGetter(Milk::cooldown)
                ).apply(instance, Milk::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, Milk> STREAM_CODEC = StreamCodec.composite(
                ItemStack.STREAM_CODEC, Milk::smallMilk,
                ItemStack.STREAM_CODEC, Milk::largeMilk,
                ByteBufCodecs.INT, Milk::cooldown,
                Milk::new
        );
    }
}
