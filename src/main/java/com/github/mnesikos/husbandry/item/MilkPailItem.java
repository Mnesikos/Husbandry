package com.github.mnesikos.husbandry.item;

import com.github.mnesikos.husbandry.dataattachments.AnimalData;
import com.github.mnesikos.husbandry.dataattachments.HusbandryAttachmentTypes;
import com.github.mnesikos.husbandry.resources.HusbandryDefinitions;
import com.github.mnesikos.husbandry.tags.HusbandryTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MilkPailItem extends Item {
    public MilkPailItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target.getType().is(HusbandryTags.EntityTypes.MILKABLE) && !target.getType().is(HusbandryTags.EntityTypes.PET_ANIMAL)) {
            double day = Math.floor(((double) target.level().dayTime() / 24000) + 1);
            int mood = AnimalData.getMood(day, target);
            if (canMilk(player, target, day, mood < 64 && Math.random() < (double) mood / 64)) {
                if (target.level().isClientSide) return InteractionResult.SUCCESS;
                int affectionIncrease = /*stages.has("animal_whisperer") || data.bribed ? 10 :*/ 5;
                AnimalData.milk(day, affectionIncrease, target);
                int affection = target.getData(HusbandryAttachmentTypes.AFFECTION);
                int hearts = (Math.min(affection, 1000)) / 100;
//                int quality = 0;
                /*if (mood >= 160) {
                    quality = getHusbandryQuality(hearts, mood, true);
                }*/
                ItemStack milkId = HusbandryDefinitions.get(target).getMilk(hearts);
//                if (milkId == "species:ichor_bottle" && hearts >= 5) quality = 3;
                /*const plushieDoubleDrops = plushieModifiers && plushieModifiers.doubleDrops;
                const plushieProcessItems = plushieModifiers && plushieModifiers.processItems;*/
                /*if (plushieProcessItems) {
                    let cheesePressOutput = cheesePressRecipes.get(milkId);
                    if (cheesePressOutput) {
                        milkId = Item.of(cheesePressOutput.output[0]).id;
                    }
                }*/
//                quality > 0 ? `{quality_food:{effects:[],quality:${quality}}}`
                if (!player.getInventory().add(milkId)) player.drop(milkId, false);
//                giveExperience(server, player, "husbandry", 30);
                for (int i = 0; i < 3; ++i) {
                    double d0 = target.getRandom().nextGaussian() * 0.02;
                    double d1 = target.getRandom().nextGaussian() * 0.02;
                    double d2 = target.getRandom().nextGaussian() * 0.02;
                    if (target.level() instanceof ServerLevel serverlevel) {
                        serverlevel.sendParticles(ParticleTypes.NOTE, target.getRandomX(target.getBbWidth() / 2), target.getY() + target.getBbHeight() * 1.2F, target.getRandomZ(target.getBbWidth() / 2), 1, d0, d1, d2, 0.01);
                        serverlevel.playSound(null, target, SoundEvents.COW_MILK, target.getSoundSource(), 1.0F, 1.0F);
                    }
                }
                return InteractionResult.CONSUME;
            } else
                target.setData(HusbandryAttachmentTypes.DAY_MILKED, day);
        }
        return InteractionResult.PASS;
    }

    public boolean canMilk(Player player, LivingEntity target, double day, boolean lostProduce) {
        HusbandryDefinitions.Milk milkDef = HusbandryDefinitions.get(target).milk();
        if (milkDef == HusbandryDefinitions.Milk.EMPTY) return false;
        boolean dayHasPassed = AnimalData.compareDay(day, AnimalData.dayDataInit(day, target, HusbandryAttachmentTypes.DAY_MILKED), milkDef.cooldown());
//        if (plushieModifiers) return dayHasPassed;
        boolean hungry = AnimalData.isHungry(day, target);

        String error = "";
        if (target.isBaby()) error = "husbandry.action.young";
        else if (hungry) error = "husbandry.action.hungry";
        else if (lostProduce) error = "husbandry.action.unhappy";
        else if (!dayHasPassed) error = "husbandry.action.cooldown";
        if (!error.isEmpty())
            player.displayClientMessage(Component.translatable(error, target.getName()).withStyle(ChatFormatting.RED), true);

        return !target.isBaby() && dayHasPassed && !hungry && !lostProduce;
    }
}
