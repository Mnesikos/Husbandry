## Basic Mechanics
- [x] This mod adds no new mods, but relies on attaching data to the entity. In SSV I did this using kubejs persistent data (per entity nbt) to keep track of the day a farm animal was interacted with in some way (ie, day last milked, day last pet, etc)
  - [x] On neo 1.21, I would imagine data attachments can handle this
  - [ ] Essentially everything from ssv relating to husbandry animals will be here
  - [ ] A small note on mood as a system, I’m not 100% happy with it and it does need some gameplay tweak so it will not be 1:1 but affection vs mood will be in the mod for sure

## Barn/coop system (new)
- [ ] This is essentially a cap on husbandry animals based off the amount of buildings
- [ ] The barn block acts as the “brain” portion, keeping track of what animals are assigned to it and if there is a valid structure for them
- [ ] Instead of having crowding checks per animal, there is a limit per barn or coop. Barn vs coop is mainly a flavor thing, as barns will support large husbandry anima while coops small
- [ ] 4 cap for basic, 8 for deluxe barn/coop

## Pregnancy and Incubation
- [ ] This mod will forgo the traditional minecraft breeding and egg system for a custom solution
- [ ] Egg laying animal will need to have their eggs incubated using an egg incubator over the course of several days
- [ ] Non-egg-laying animal will be impregnated via a magic potion, provided two of the same type are assigned to the barn. Over the course of several days, the pregnant farm animal will not produce product, until birth. Pregnancy duration is data driven, probably a datamap

## Plushies
- [ ] The plushie system will still exist, with minor tweaks. Instead of having every plushie be a separate id, plushies will essentially be one item/block that mirrors the appearance of another block.
- [ ] Plushie traits effects will be data driven
- [ ] I do want to add a “build a bear workshop” type system where players can recycle plushies into parts to build their own, but that might be a later on thing

## Blocks/Items
- [ ] Barn and coop blocks
- [ ] Autograbber - Fueled way to harvest from animals, should match whatever the upcoming artisan machine mod does
- [ ] Auto petter
- [ ] Egg incubator - Hatches eggs over a data/recipe driven period of days
- [ ] Feeding trough
- [x] Feeds