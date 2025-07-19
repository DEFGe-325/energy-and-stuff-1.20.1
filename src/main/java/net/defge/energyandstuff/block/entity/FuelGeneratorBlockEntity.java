package net.defge.energyandstuff.block.entity;

import net.defge.energyandstuff.block.custom.FuelGeneratorBlock;
import net.defge.energyandstuff.block.entity.util.ModEnergyStorage;
import net.defge.energyandstuff.block.entity.util.TickableBlockEntity;
import net.defge.energyandstuff.screen.FuelGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FuelGeneratorBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == GENERATOR_SLOT) {
                return stack.getCapability(ForgeCapabilities.ENERGY).isPresent();
            }
            return true;
        }


        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {

            return super.extractItem(slot, amount, simulate);
        }
    };

    private static final int FUEL_SLOT = 0;
    private static final int GENERATOR_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    private final ModEnergyStorage energy = new ModEnergyStorage(10000, 0, 10000, 0);
    private final LazyOptional<ModEnergyStorage> energyOptional = LazyOptional.of(() -> this.energy);

    private int burnTime = 0, maxBurnTime = 0;

    public LazyOptional<ModEnergyStorage> getEnergyOptional() {
        return this.energyOptional;
    }

    public ModEnergyStorage getEnergy() {
        return this.energy;
    }

    private void sendUpdate() {
        setChanged();

        if (this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    protected final ContainerData data;

    public FuelGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FUEL_GENERATOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FuelGeneratorBlockEntity.this.energy.getEnergyStored();
                    case 1 -> FuelGeneratorBlockEntity.this.energy.getMaxEnergyStored();
                    case 2 -> FuelGeneratorBlockEntity.this.burnTime;
                    case 3 -> FuelGeneratorBlockEntity.this.maxBurnTime;
                    default -> throw new UnsupportedOperationException("Unexpected value: " + pIndex);
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FuelGeneratorBlockEntity.this.energy.setEnergy(pValue);
                    case 2 -> FuelGeneratorBlockEntity.this.burnTime = pValue;
                    case 3 -> FuelGeneratorBlockEntity.this.maxBurnTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        } else if (cap == ForgeCapabilities.ENERGY) {
            return energyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        this.energyOptional.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.energyandstuff.fuel_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FuelGeneratorMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        if (itemHandler != null) {
            pTag.put("inventory", itemHandler.serializeNBT());
        }
        if (energy != null) {
            CompoundTag energyTag = new CompoundTag();
            energyTag.putInt("Energy", energy.getEnergyStored());
            pTag.put("energy", energyTag);
        }
        pTag.putInt("burnTime", this.burnTime);
        pTag.putInt("maxBurnTime", this.maxBurnTime);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("energy", Tag.TAG_COMPOUND)) {
            CompoundTag energyTag = pTag.getCompound("energy");
            energy.deserializeNBT(energyTag);
        }

        if (pTag.contains("inventory", Tag.TAG_COMPOUND)) {
            itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        }
        if (pTag.contains("burnTime", Tag.TAG_INT)) {
            burnTime = pTag.getInt("burnTime");
        }
        if (pTag.contains("maxBurnTime", Tag.TAG_INT)) {
            maxBurnTime = pTag.getInt("maxBurnTime");
        }
    }


    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide())
            return;

        if (burnTime > 0) {
            burnTime--;
        }
        updateLitState();

        if (this.energy.getEnergyStored() < this.energy.getMaxEnergyStored()) {
            if (this.burnTime <= 0) {
                ItemStack fuelStack = this.itemHandler.getStackInSlot(0);
                if (canBurn(fuelStack)) {
                    this.burnTime = this.maxBurnTime = getBurnTime(fuelStack);

                    ItemStack remainingItem = fuelStack.getCraftingRemainingItem();
                    if (!remainingItem.isEmpty()) {
                        this.itemHandler.setStackInSlot(0, remainingItem);
                    } else {
                        fuelStack.shrink(1);
                    }

                    sendUpdate();
                }
            } else {
                this.burnTime--;
                this.energy.addEnergy(1);
                sendUpdate();
            }
        }

        if (!itemHandler.getStackInSlot(GENERATOR_SLOT).isEmpty()) {
            ItemStack stack = itemHandler.getStackInSlot(GENERATOR_SLOT);
            LazyOptional<net.minecraftforge.energy.IEnergyStorage> energyCap = stack.getCapability(ForgeCapabilities.ENERGY);

            energyCap.ifPresent(itemEnergy -> {
                int energyToTransfer = Math.min(energy.getEnergyStored(), 100);
                int acceptedEnergy = itemEnergy.receiveEnergy(energyToTransfer, false);
                energy.extractEnergy(acceptedEnergy, false);
            });
        }
    }

    public boolean canBurn(ItemStack stack) {
        return getBurnTime(stack) > 0;
    }

    public int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
    }

    public int getMaxBurnTime() {
        return this.maxBurnTime;
    }

    public IItemHandler getItemHandler() {
        return this.itemHandler;
    }

    public void updateLitState() {
        boolean shouldBeLit = (burnTime > 0);
        BlockState currentState = level.getBlockState(worldPosition);
        if (currentState.hasProperty(FuelGeneratorBlock.LIT) &&
                currentState.getValue(FuelGeneratorBlock.LIT) != shouldBeLit) {
            level.setBlock(worldPosition, currentState.setValue(FuelGeneratorBlock.LIT, shouldBeLit), 3);
        }
    }

    public void consumeFuel() {
        ItemStack stack = itemHandler.getStackInSlot(FUEL_SLOT);

        if (!stack.isEmpty()) {
            ItemStack remainingItem = stack.getCraftingRemainingItem();

            if (!remainingItem.isEmpty()) {
                itemHandler.setStackInSlot(FUEL_SLOT, remainingItem);
            } else {
                stack.shrink(1);
            }
        }
    }
}