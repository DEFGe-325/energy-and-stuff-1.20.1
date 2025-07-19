package net.defge.energyandstuff.item;

import net.defge.energyandstuff.EnergyAndStuff;
import net.defge.energyandstuff.item.custom.Wand;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EnergyAndStuff.MOD_ID);

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUPERCONDUCTING_BRASS_INGOT = ITEMS.register("superconducting_brass_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_COIL = ITEMS.register("copper_coil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASS_BLADE = ITEMS.register("brass_blade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOLAR_PANEL_ITEM = ITEMS.register("solar_panel_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WAND = ITEMS.register("wand",
            () -> new Wand((new Item.Properties().stacksTo(1))));

    public static final RegistryObject<Item> POWER_COIL = ITEMS.register("power_coil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FOCUSING_LENS = ITEMS.register("focusing_lens",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DISCHARGE_CATALYST = ITEMS.register("discharge_catalyst",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_VOLTAGE_COIL = ITEMS.register("high_voltage_coil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STORM_BLADE = ITEMS.register("storm_blade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAVI_MAGNETRON = ITEMS.register("gravi_magnetron",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OVERCHARGED_SPHERE = ITEMS.register("overcharged_sphere",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BASIC_HANDLE = ITEMS.register("basic_handle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STRENGTH_SWITCH = ITEMS.register("strength_switch",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QUICK_GRIP = ITEMS.register("quick_grip",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PULSE_SWITCH = ITEMS.register("pulse_switch",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COVER_CAP = ITEMS.register("cover_cap",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BATTERY_LEVEL1 = ITEMS.register("battery_level1",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BATTERY_LEVEL2 = ITEMS.register("battery_level2",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BATTERY_LEVEL3 = ITEMS.register("battery_level3",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register("solar_panel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POWER_NODE = ITEMS.register("power_node",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
