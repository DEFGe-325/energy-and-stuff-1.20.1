package net.defge.energyandstuff.item.custom.util;

import net.defge.energyandstuff.item.custom.Wand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WandCapabilityHandler {
    private static final ResourceLocation ENERGY_CAP = new ResourceLocation("energyandstuff", "wand_energy");

    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.getItem() instanceof Wand) {
            event.addCapability(ENERGY_CAP, new WandEnergyCapability());
        }
    }
}
