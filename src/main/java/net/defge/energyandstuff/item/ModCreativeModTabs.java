package net.defge.energyandstuff.item;

import net.defge.energyandstuff.EnergyAndStuff;
import net.defge.energyandstuff.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EnergyAndStuff.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ENERGY_AND_STUFF_TAB = CREATIVE_MODE_TABS.register("energyandstufftab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COPPER_NUGGET.get()))
                    .title(Component.translatable("creativetab.energyandstufftab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.COPPER_NUGGET.get());
                        pOutput.accept(ModItems.SUPERCONDUCTING_BRASS_INGOT.get());
                        pOutput.accept(ModItems.COPPER_COIL.get());
                        pOutput.accept(ModItems.BRASS_BLADE.get());
                        pOutput.accept(ModItems.SOLAR_PANEL_ITEM.get());

                        pOutput.accept(ModItems.WAND.get());

                        pOutput.accept(ModBlocks.FUEL_GENERATOR.get());

                        pOutput.accept(ModBlocks.WAND_CUSTOMIZATION_TABLE.get());

                        pOutput.accept(ModItems.POWER_COIL.get());
                        pOutput.accept(ModItems.FOCUSING_LENS.get());
                        pOutput.accept(ModItems.DISCHARGE_CATALYST.get());
                        pOutput.accept(ModItems.HIGH_VOLTAGE_COIL.get());
                        pOutput.accept(ModItems.STORM_BLADE.get());
                        pOutput.accept(ModItems.GRAVI_MAGNETRON.get());
                        pOutput.accept(ModItems.OVERCHARGED_SPHERE.get());

                        pOutput.accept(ModItems.BASIC_HANDLE.get());
                        pOutput.accept(ModItems.STRENGTH_SWITCH.get());
                        pOutput.accept(ModItems.QUICK_GRIP.get());
                        pOutput.accept(ModItems.PULSE_SWITCH.get());

                        pOutput.accept(ModItems.COVER_CAP.get());
                        pOutput.accept(ModItems.BATTERY_LEVEL1.get());
                        pOutput.accept(ModItems.BATTERY_LEVEL2.get());
                        pOutput.accept(ModItems.BATTERY_LEVEL3.get());
                        pOutput.accept(ModItems.SOLAR_PANEL.get());
                        pOutput.accept(ModItems.POWER_NODE.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
