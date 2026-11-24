package com.github.mnesikos.husbandry.event;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.dataattachments.AnimalData;
import com.github.mnesikos.husbandry.dataattachments.HusbandryAttachmentTypes;
import com.github.mnesikos.husbandry.item.AnimalFeed;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import com.github.mnesikos.husbandry.resources.HusbandryDefinitions;
import com.github.mnesikos.husbandry.tags.HusbandryTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.ArrayList;

@EventBusSubscriber(modid = Husbandry.MOD_ID)
public class HusbandryEvents {
    @SubscribeEvent
    public static void playerInteractWithEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() == InteractionHand.OFF_HAND || event.getLevel().isClientSide) return;
        Entity target = event.getTarget();
        boolean isPetAnimal = target.getType().is(HusbandryTags.EntityTypes.PET_ANIMAL);
        if (target.getType().is(HusbandryTags.EntityTypes.HUSBANDRY_ANIMAL) || isPetAnimal) {
            Player player = event.getEntity();
            double day = Math.floor(((double) event.getLevel().dayTime() / 24000) + 1);
            int affection = target.getData(HusbandryAttachmentTypes.AFFECTION);
            double dayLastPet = AnimalData.dayDataInit(day, target, HusbandryAttachmentTypes.DAY_PET);
            ItemStack itemStack = event.getItemStack();
            AnimalFeed animalFeed = itemStack.getItemHolder().getData(AnimalFeed.ANIMAL_FEED);
            boolean peckish = AnimalData.isPeckish(day, target) && animalFeed == null;
            boolean hungry = AnimalData.isHungry(day, target) && animalFeed == null;
            int mood = AnimalData.getMood(day, target);
            player.swing(event.getHand());
            int hearts = (Math.min(affection, 1000)) / 100;
            if (hearts < 0) hearts = 0;

            if (AnimalData.isNextDay(day, dayLastPet)) {
                AnimalData.pet(day, getAffectionIncrease(target, hungry), target);
                ParticleOptions particleoptions = (/*!data.clockwork && player.isFake()) ||*/ hungry) ? ParticleTypes.ANGRY_VILLAGER : ParticleTypes.HEART;

                if (event.getLevel() instanceof ServerLevel serverlevel)
                    serverlevel.sendParticles(particleoptions, target.getX(), target.getY() + target.getBbHeight() * 1.2F, target.getZ(), 1, 0, 0.1, 0, 0.01);
//                global.giveExperience(server, player, "husbandry", Math.max(10, 20 * hearts));
//                if (!livableArea && !data.clockwork) sendPlayerMessage(player, level, Component.translatable("husbandry.crowded", target.getName()).withStyle(ChatFormatting.RED));
                if (peckish)
                    player.displayClientMessage(Component.translatable("husbandry.peckish", target.getName()).withStyle(ChatFormatting.GOLD), true);
                else if (hungry)
                    player.displayClientMessage(Component.translatable("husbandry.hungry", target.getName()).withStyle(ChatFormatting.RED), true);

                // Raise Max health
                /*const affectionHealth = hearts * 4;
                if (target.maxHealth < affectionHealth) {
                    target.setMaxHealth(affectionHealth);
                    target.setHealth(affectionHealth);
                }*/
            }

            if (itemStack.is(HusbandryItems.MOOD_SCANNER)) {
                ChatFormatting nameColor = peckish ? ChatFormatting.GOLD : hungry ? ChatFormatting.RED : ChatFormatting.GREEN;
                MutableComponent name = target.getName().plainCopy().withStyle(nameColor);
                ArrayList<String> icons = new ArrayList<>();
                if (mood < 64) icons.add("☹");
                if (mood > 160) icons.add("☺");
                //        if (data.animalCracker) icons.add("🡅");
                //        if (data.clockwork) icons.add("⚙");
                //        if (data.bff) icons.add("❤");
                //        if (data.bribed) icons.add("💰");
                MutableComponent iconString = Component.empty().withStyle(nameColor);
                icons.forEach(icon -> iconString.append(" " + icon));
                int maxHearts = 10;
                MutableComponent filledHearts = Component.literal(hearts > 0 ? "❤".repeat(Math.min(hearts, maxHearts)) : "").withStyle(ChatFormatting.RED);
                MutableComponent emptyHearts = Component.literal(hearts < maxHearts ? "❤".repeat(maxHearts - hearts) : "").withStyle(ChatFormatting.BLACK);
                player.displayClientMessage(name.append(" ").append(filledHearts).append(emptyHearts).append(iconString), true);
            }

            if (!isPetAnimal) {
                if (animalFeed != null) {
                    if (AnimalData.isNextDay(day, AnimalData.dayDataInit(day, target, HusbandryAttachmentTypes.DAY_FED))) {
                        if (target instanceof LivingEntity livingEntity) livingEntity.heal(4);
//                        global.giveExperience(server, player, "husbandry", 20);
                        int affectionMultiplier = /*player.stages.has("animal_whisperer") || data.bribed ? 2 :*/ 1;
//                        if (player.stages.has("animal_fancy")) affectionMultiplier += 0.5;
                        AnimalData.feed(day, Math.min(animalFeed.handFed() * affectionMultiplier, 100), target);

                        for (int i = 0; i < 5; ++i) {
                            double d0 = target.getRandom().nextGaussian() * 0.1;
                            double d1 = target.getRandom().nextGaussian() * 0.1;
                            double d2 = target.getRandom().nextGaussian() * 0.1;
                            if (target.level() instanceof ServerLevel serverlevel) {
                                serverlevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, target.getRandomX(target.getBbWidth() / 2), target.getY() + target.getBbHeight() * 1.2F, target.getRandomZ(target.getBbWidth() / 2), 1, d0, d1, d2, 0.01);
                                serverlevel.playSound(null, target, itemStack.getEatingSound(), target.getSoundSource(), 1.0F, 1.0F);
                            }
                        }
                        itemStack.consume(1, player);
                        player.getCooldowns().addCooldown(itemStack.getItem(), 10);
                    }
                }

                boolean lostProduce = mood < 64 && Math.random() < (double) mood / 64;
                if (!lostProduce && !HusbandryDefinitions.get(target).forages().isEmpty()) {
                    double dayLastDroppedSpecial = AnimalData.dayDataInit(day, target, HusbandryAttachmentTypes.DAY_DROPPED_SPECIAL);
                    if (AnimalData.isNextDay(day, dayLastDroppedSpecial)) {
//                    if (*//*stages.has("coopmaster") &&*//* (*//*plushieModifiers ? coopMasterAnimals.includes(data.type) :*//* target.getType().is(HusbandryTags.EntityTypes.COOPMASTER_BIRD))) golden egg
//                    if (data.bff && stages.has("bff")) pristmatic shard
//                    if (player && !player.isFake() && !stages.has("animal_fancy")) animal fancy
//                    if (stages.has("reaping_scythe")) diamond heart
//                    if (plushieModifiers && plushieModifiers.newDrops && plushieModifiers.newDrops.length > 0) plushieModifiers.newDrops.forEach((drop)

                        ItemStack resolvedItem = HusbandryDefinitions.get(target).getRandomForage(target.getRandom(), hearts);
                        if (BuiltInRegistries.ITEM.getKey(resolvedItem.getItem()).getPath().contains("large"))
                            if (Math.random() > (double) (mood + hearts * 10) / 256)
                                resolvedItem = ItemStack.EMPTY.copy();
                        if (!resolvedItem.isEmpty() && (!hungry /*|| plushieModifiers*/)) {
//                    if (plushieModifiers) data.affection = affection + (player.stages.has("animal_whisperer") ? 20 : 10);
//                    if (resolvedHasQuality && mood >= 160) quality = global.getHusbandryQuality(hearts, mood); // quality > 0 ? `{quality_food:{effects:[],quality:${quality}}}`
                            if (!player.getInventory().add(resolvedItem)) player.drop(resolvedItem, false);
//                        global.giveExperience(server, player, "husbandry", 60);
                            for (int i = 0; i < 3; ++i) {
                                double d0 = target.getRandom().nextGaussian() * 0.02;
                                double d1 = target.getRandom().nextGaussian() * 0.02;
                                double d2 = target.getRandom().nextGaussian() * 0.02;
                                if (player.level() instanceof ServerLevel serverlevel) {
                                    serverlevel.sendParticles(ParticleTypes.FIREWORK, target.getRandomX(target.getBbWidth() / 2), target.getY() + target.getBbHeight() * 1.2F, target.getRandomZ(target.getBbWidth() / 2), 1, d0, d1, d2, 0.01);
                                    serverlevel.playSound(null, player, SoundEvents.ITEM_PICKUP, player.getSoundSource(), 1.0F, 1.0F);
                                }
                            }
                        }
                        AnimalData.dropSpecial(day, target);
                    }
                }
            }
            Husbandry.LOGGER.debug("Current affection {}, mood {} for {} at {}.", target.getData(HusbandryAttachmentTypes.AFFECTION), target.getData(HusbandryAttachmentTypes.MOOD), target.getName().getString(), target.blockPosition());
        }
    }

    private static int getAffectionIncrease(Entity target, boolean hungry) {
        int affectionMultiplier = /*player.stages.has("animal_whisperer") || data.bribed ? 2 :*/ 1;
//        if (player.stages.has("animal_fancy")) affectionMultiplier += 1;
        int affectionIncrease = 10 * affectionMultiplier;
        if (target instanceof AgeableMob ageableMob && ageableMob.isBaby())
            affectionIncrease *= (/*player.stages.has("fostering") ? 10 :*/ 2);

//        let livableArea = global.getAnimalIsNotCramped(target, 1.1);
        if (hungry /*|| (!data.clockwork && player.isFake()) || !livableArea*/)
            affectionIncrease -= (hungry ? 15 : 25);
        return affectionIncrease;
    }

    @SubscribeEvent
    public static void registerDataPack(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(HusbandryDefinitions.KEY, HusbandryDefinitions.CODEC, HusbandryDefinitions.CODEC);
    }

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(AnimalFeed.ANIMAL_FEED);
    }
}
