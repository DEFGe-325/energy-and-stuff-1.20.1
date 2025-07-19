package net.defge.energyandstuff.item.custom.util;

import net.defge.energyandstuff.item.custom.WandModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WandOverrideList extends ItemOverrides {
    private final Map<ItemStack, WandModel> cachedModels = new HashMap<>();

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        return cachedModels.computeIfAbsent(stack, s -> new WandModel(() -> originalModel, loadParts(s), s));
    }

    private Map<String, Supplier<BakedModel>> loadParts(ItemStack stack) {
        Map<String, Supplier<BakedModel>> parts = new HashMap<>();
        CompoundTag tag = stack.getTag();

        if (tag != null) {
            parts.put("wand_cap", () -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("mod_id:item/wand_" + tag.getString("wand_cap"))));
            parts.put("wand_handle", () -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("mod_id:item/wand_" + tag.getString("wand_handle"))));
            parts.put("wand_extra", () -> Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("mod_id:item/wand_" + tag.getString("wand_extra"))));
        }

        return parts;
    }
}
