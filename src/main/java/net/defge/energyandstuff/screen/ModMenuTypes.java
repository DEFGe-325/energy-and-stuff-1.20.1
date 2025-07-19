package net.defge.energyandstuff.screen;

import net.defge.energyandstuff.EnergyAndStuff;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, EnergyAndStuff.MOD_ID);

    public static final RegistryObject<MenuType<FuelGeneratorMenu>> FUEL_GENERATOR_MENU =
            registerMenuType("fuel_generator_menu", FuelGeneratorMenu::new);

    public static final RegistryObject<MenuType<WandCustomizationTableMenu>> WAND_CUSTOMIZATION_TABLE_MENU =
            registerMenuType("wand_customization_table_menu", WandCustomizationTableMenu::new);


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
