package net.defge.energyandstuff.item.custom.util;

import net.defge.energyandstuff.item.custom.WandModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WandOverrideModel implements IOverrideModel
{
    @Override
    public BakedModel getModel(ItemStack stack)
    {
        return new WandModel(() -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("energyandstuff:item/wand_base")), loadParts(stack), stack);
    }

    private Map<String, Supplier<BakedModel>> loadParts(ItemStack stack)
    {
        Map<String, Supplier<BakedModel>> parts = new HashMap<>();
        CompoundTag tag = stack.getTag();

        if (tag != null) {
            parts.put("wand_cap", () -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("energyandstuff:item/wand_" + tag.getString("wand_cap"))));
            parts.put("wand_handle", () -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("energyandstuff:item/wand_" + tag.getString("wand_handle"))));
            parts.put("wand_extra", () -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("energyandstuff:item/wand_" + tag.getString("wand_extra"))));
        }

        return parts;
    }
}
