package net.defge.energyandstuff.item.custom;

import net.defge.energyandstuff.block.entity.util.ModEnergyStorage;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.Item;
import net.minecraftforge.energy.IEnergyStorage;

public class Wand extends Item {
    private final ModEnergyStorage energyStorage;

    public Wand(Properties properties) {
        super(properties);
        this.energyStorage = new ModEnergyStorage(1000, 500, 500);
    }

    // Потребление энергии
    public int extractEnergy(int amount, boolean simulate) {
        return energyStorage.extractEnergy(amount, simulate);
    }

    // Зарядка энергии
    public int receiveEnergy(int amount, boolean simulate) {
        return energyStorage.receiveEnergy(amount, simulate);
    }

    // Получение текущего уровня энергии
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    // Получение максимального запаса энергии
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    // Проверка, может ли энергия извлекаться
    public boolean canExtract() {
        return true;
    }

    // Проверка, может ли энергия заряжаться
    public boolean canReceive() {
        return true;
    }

}