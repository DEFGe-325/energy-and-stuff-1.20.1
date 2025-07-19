package net.defge.energyandstuff.item.custom.util;

import net.defge.energyandstuff.block.entity.util.ModEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WandEnergyCapability implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() -> new ModEnergyStorage(1000, 500, 500));

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return capability == ForgeCapabilities.ENERGY ? energyStorage.cast() : LazyOptional.empty();
    }
}