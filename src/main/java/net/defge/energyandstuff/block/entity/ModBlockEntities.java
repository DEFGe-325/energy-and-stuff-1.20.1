package net.defge.energyandstuff.block.entity;

import net.defge.energyandstuff.EnergyAndStuff;
import net.defge.energyandstuff.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EnergyAndStuff.MOD_ID);

    public static final RegistryObject<BlockEntityType<FuelGeneratorBlockEntity>> FUEL_GENERATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fuel_generator_block_entity", () ->
                    BlockEntityType.Builder.of(FuelGeneratorBlockEntity::new,
                            ModBlocks.FUEL_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<WandCustomizationTableBlockEntity>> WAND_CUSTOMIZATION_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("wand_customization_table_block_entity", () ->
                    BlockEntityType.Builder.of(WandCustomizationTableBlockEntity::new,
                            ModBlocks.WAND_CUSTOMIZATION_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
