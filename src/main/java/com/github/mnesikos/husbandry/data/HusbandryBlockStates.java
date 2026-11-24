package com.github.mnesikos.husbandry.data;

import com.github.mnesikos.husbandry.Husbandry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class HusbandryBlockStates extends BlockStateProvider {
    public HusbandryBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Husbandry.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
