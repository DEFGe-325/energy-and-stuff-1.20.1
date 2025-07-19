package net.defge.energyandstuff.event;

import net.defge.energyandstuff.EnergyAndStuff;
import net.defge.energyandstuff.block.entity.ModBlockEntities;
import net.defge.energyandstuff.block.entity.renderer.WandCustomizationTableBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EnergyAndStuff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.WAND_CUSTOMIZATION_TABLE_BLOCK_ENTITY.get(), WandCustomizationTableBlockEntityRenderer::new);
    }
}
