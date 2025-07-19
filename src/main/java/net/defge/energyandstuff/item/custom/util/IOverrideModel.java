package net.defge.energyandstuff.item.custom.util;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public interface IOverrideModel {
    BakedModel getModel(ItemStack stack);
}
