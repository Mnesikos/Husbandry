package com.github.mnesikos.husbandry.dataattachments;

import com.github.mnesikos.husbandry.Husbandry;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class HusbandryAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> REGISTRAR = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Husbandry.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> AFFECTION = REGISTRAR.register("affection", () -> AttachmentType
            .builder(() -> 0)
            .serialize(Codec.INT)
            .sync(StreamCodec.of(FriendlyByteBuf::writeVarInt, FriendlyByteBuf::readVarInt))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_PET = REGISTRAR.register("day_pet", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_FED = REGISTRAR.register("day_fed", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_MILKED = REGISTRAR.register("day_milked", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_DROPPED_SPECIAL = REGISTRAR.register("day_dropped_special", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_MAGIC_HARVESTED = REGISTRAR.register("day_magic_harvested", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
    public static final Supplier<AttachmentType<Integer>> MOOD = REGISTRAR.register("mood", () -> AttachmentType
            .builder(() -> 0)
            .serialize(Codec.INT)
            .sync(StreamCodec.of(FriendlyByteBuf::writeVarInt, FriendlyByteBuf::readVarInt))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_MOOD_SET = REGISTRAR.register("day_mood_set", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
    public static final Supplier<AttachmentType<Double>> DAY_BRED = REGISTRAR.register("day_bred", () -> AttachmentType
            .builder(() -> 0D)
            .serialize(Codec.DOUBLE)
            .sync(StreamCodec.of(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble))
            .build());
}
