package com.github.mnesikos.husbandry.dataattachments;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.tags.HusbandryTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AnimalData {
    public static boolean isNextDay(double day, double checkedDay) {
        return compareDay(day, checkedDay, 1);
    }

    public static boolean compareDay(double day, double checkedDay, int amount) {
        return day < checkedDay || day - checkedDay >= amount;
    }

    public static double dayDataInit(double day, Entity target, Supplier<AttachmentType<Double>> type) {
        double fresh = type == HusbandryAttachmentTypes.DAY_PET || type == HusbandryAttachmentTypes.DAY_FED ? day - 1 : -1;
        if (!target.hasData(type)) {
            target.setData(type, fresh);
            Husbandry.LOGGER.debug("Initialized data for {}: {}", NeoForgeRegistries.ATTACHMENT_TYPES.getKey(type.get()), fresh);
            return fresh;
        }

        double dayData = target.getData(type);
        if (day < dayData || dayData - day > 5000) {
            target.setData(type, fresh);
            Husbandry.LOGGER.debug("Forgiving data for {}, reset to: {}", NeoForgeRegistries.ATTACHMENT_TYPES.getKey(type.get()), fresh);
            return fresh;
        }
        return dayData;
    }

    public static boolean isPeckish(double day, Entity target) {
        return !target.getType().is(HusbandryTags.EntityTypes.PET_ANIMAL) && day - target.getData(HusbandryAttachmentTypes.DAY_FED) == 1;
    }

    public static boolean isHungry(double day, Entity target) {
        return !target.getType().is(HusbandryTags.EntityTypes.PET_ANIMAL) && day - target.getData(HusbandryAttachmentTypes.DAY_FED) > 1;
    }

    public static boolean isCramped(Entity target, double scale) {
        return isCramped(target, scale, false);
    }

    public static boolean isCramped(Entity target, double scale, boolean applyGlowing) {
        List<Entity> entities = target.level().getEntities(target, target.getBoundingBox().inflate(scale), entity -> entity.getType().is(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL));
        boolean cramped = entities.size() > 5;
        if (cramped && applyGlowing) {
            entities.forEach((entity) -> {
                if (entity instanceof LivingEntity livingEntity)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
            });
        }
        return cramped;
    }

    public static int getMood(double day, Entity target) {
        if (!target.hasData(HusbandryAttachmentTypes.DAY_MOOD_SET)) {
            target.setData(HusbandryAttachmentTypes.MOOD, 256);
            target.setData(HusbandryAttachmentTypes.DAY_MOOD_SET, day);
        }

        if (isNextDay(day, target.getData(HusbandryAttachmentTypes.DAY_MOOD_SET))) {
            int moodDebuffs = 0;
            float moodImpactModifier = target.getType().is(HusbandryTags.EntityTypes.TIER_TWO) ? 1.25F : target.getType().is(HusbandryTags.EntityTypes.TIER_THREE) ? 1.5F : 1.0F;
            if (moodImpactModifier > 1.0F)
                Husbandry.LOGGER.debug("Mood Debuffs multiplied by {}x due to breed", moodImpactModifier);
            if (compareDay(day, target.getData(HusbandryAttachmentTypes.DAY_PET), 2)) {
                moodDebuffs += 96;
                Husbandry.LOGGER.debug("-96 - Too long since last pet");
            }
            if (!target.getType().is(HusbandryTags.EntityTypes.PET_ANIMAL) && compareDay(day, target.getData(HusbandryAttachmentTypes.DAY_FED), 2)) {
                moodDebuffs += 128;
                Husbandry.LOGGER.debug("-128 - Too long since last fed");
            }
            Level level = target.level();
            BlockPos pos = target.blockPosition();
            if (target.getType().is(HusbandryTags.EntityTypes.COLD)) {
                int requiredBlocks = countNearbyBlocks(level, target, 5, 5, state -> state.is(HusbandryTags.Blocks.COLD));
                if (requiredBlocks < 5) {
                    int coldDebuff = 32 - (32 * requiredBlocks) / 5;
                    moodDebuffs += coldDebuff;
                    Husbandry.LOGGER.debug("-{} - Winter animal needs more cold blocks nearby", coldDebuff);
                }
            } /*else {
                if (global.getSeasonFromBiome(level, pos) == "winter" && level.canSeeSky(pos)) {
                    moodDebuffs += 32;
                    Husbandry.LOGGER.debug("-32 - Left outside during winter");
                }
            }*/
            if (isCramped(target, 1.1)) {
                moodDebuffs += 96;
                Husbandry.LOGGER.debug("-96 - More than 5 animals in close proximity");
            }
            if (level.isRainingAt(pos) && level.canSeeSky(pos)) {
                moodDebuffs += 32;
                Husbandry.LOGGER.debug("-32 - Outside in the rain");
            }
            if (moodDebuffs > 0) Husbandry.LOGGER.debug("Total mood debuffs before mods: {}", moodDebuffs);
            moodDebuffs = Math.round(moodDebuffs * moodImpactModifier);
            /*if (day - data.getInt("ageLastBoosted") == 1) {
                moodDebuffs -= 24;
                Husbandry.LOGGER.debug("+24 - Boosted from food");
            }*/
            /*if (data.clockwork) {
                moodDebuffs -= 64;
                Husbandry.LOGGER.debug("+64 - Boosted from clockwork");
            }*/
            Husbandry.LOGGER.debug("Final mood: {}/256", 256 - moodDebuffs);
            target.setData(HusbandryAttachmentTypes.MOOD, Math.max(0, 256 - moodDebuffs));
            target.setData(HusbandryAttachmentTypes.DAY_MOOD_SET, day);
        }
        return target.getData(HusbandryAttachmentTypes.MOOD);
    }

    private static int countNearbyBlocks(Level level, Entity target, int radius, int max, Predicate<BlockState> predicate) {
        BlockPos blockpos = target.blockPosition();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        int count = 0;
        for (int i = 0; i <= radius; i = i > 0 ? -i : 1 - i) {
            for (int j = 0; j < radius; ++j) {
                for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, k, i - 1, l);
                        if (blockpos.closerThan(blockpos$mutableblockpos, radius) && predicate.test(level.getBlockState(blockpos$mutableblockpos)))
                            count++;
                        if (count >= max) return count;
                    }
                }
            }
        }

        return count;
    }

    public static void pet(double day, int affectionIncrease, Entity target) {
        target.setData(HusbandryAttachmentTypes.AFFECTION, adjustAffection(affectionIncrease, target));
        target.setData(HusbandryAttachmentTypes.DAY_PET, day);
        Husbandry.LOGGER.debug("Increased Affection by: {} from petting", affectionIncrease);
    }

    public static void feed(double day, int affectionIncrease, Entity target) {
        target.setData(HusbandryAttachmentTypes.AFFECTION, adjustAffection(affectionIncrease, target));
        target.setData(HusbandryAttachmentTypes.DAY_FED, day);
        Husbandry.LOGGER.debug("Increased Affection by: {} from feeding", affectionIncrease);
    }

    public static void milk(double day, int affectionIncrease, Entity target) {
        target.setData(HusbandryAttachmentTypes.AFFECTION, adjustAffection(affectionIncrease, target));
        target.setData(HusbandryAttachmentTypes.DAY_MILKED, day);
        Husbandry.LOGGER.debug("Increased Affection by: {} from milking", affectionIncrease);
    }

    public static void dropSpecial(double day, Entity target) {
        target.setData(HusbandryAttachmentTypes.DAY_DROPPED_SPECIAL, day);
    }

    private static int adjustAffection(int i, Entity target) {
        return Math.clamp(target.getData(HusbandryAttachmentTypes.AFFECTION) + i, 0, 1075);
    }
}
