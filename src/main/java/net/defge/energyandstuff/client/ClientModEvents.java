package net.defge.energyandstuff.client;

import net.defge.energyandstuff.item.custom.util.ModelOverrides;
import net.defge.energyandstuff.item.custom.util.WandOverrideModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.defge.energyandstuff.item.ModItems;

@Mod.EventBusSubscriber(modid = "energyandstuff", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModelOverrides.register(ModItems.WAND.get(), new WandOverrideModel());
    }
}
