package net.defge.energyandstuff.sound;

import net.defge.energyandstuff.EnergyAndStuff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EnergyAndStuff.MOD_ID);

    public static final RegistryObject<SoundEvent> WAND_CREATION = registerSoundEvents("wand_creation");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUNDS_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(EnergyAndStuff.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS_EVENTS.register(eventBus);
    }
}
