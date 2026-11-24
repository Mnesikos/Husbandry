package com.github.mnesikos.husbandry.block;

import com.github.mnesikos.husbandry.Husbandry;
import com.github.mnesikos.husbandry.item.HusbandryItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HusbandryBlocks {
    public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(Husbandry.MOD_ID);

    public static final DeferredBlock<Block> BARN_STATION = registerWithItem("barn_station", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> COOP_STATION = registerWithItem("coop_station", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> TROUGH = registerWithItem("trough", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DELUXE_TROUGH = registerWithItem("deluxe_trough", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> INCUBATOR = registerWithItem("incubator", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> AUTO_GRABBER = registerWithItem("auto_grabber", () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> AUTO_PETTER = registerWithItem("auto_petter", () -> new Block(BlockBehaviour.Properties.of()));

    private static <T extends Block> DeferredBlock<T> registerWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> registryObject = REGISTRAR.register(name, block);
        HusbandryItems.REGISTRAR.registerSimpleBlockItem(name, registryObject);
        return registryObject;
    }
}
