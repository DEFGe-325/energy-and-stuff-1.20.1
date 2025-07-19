package net.defge.energyandstuff.block.entity;

import net.defge.energyandstuff.sound.ModSounds;
import net.defge.energyandstuff.item.ModItems;
import net.defge.energyandstuff.screen.WandCustomizationTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandCustomizationTableBlockEntity extends BlockEntity implements MenuProvider {

    protected final ContainerData data;


    public WandCustomizationTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WAND_CUSTOMIZATION_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return 0;
            }

            @Override
            public void set(int i, int i1) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };

    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == CAP_SLOT) {
                TagKey<Item> wandCapTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("energyandstuff", "wand_cap"));
                return stack.is(wandCapTag);
            }

            if (slot == HANDLE_SLOT) {
                TagKey<Item> wandHandleTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("energyandstuff", "wand_handle"));
                return stack.is(wandHandleTag);
            }

            if (slot == EXTRA_SLOT) {
                TagKey<Item> wandExtraTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("energyandstuff", "wand_extra"));
                return stack.is(wandExtraTag);
            }

            if (slot == DYE_SLOT) {
                TagKey<Item> wandDyeTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "dyes"));
                return stack.is(wandDyeTag);
            }

            if (slot == WAND_SLOT) {
                boolean otherSlotsFilled = !itemHandler.getStackInSlot(CAP_SLOT).isEmpty() ||
                        !itemHandler.getStackInSlot(HANDLE_SLOT).isEmpty() ||
                        !itemHandler.getStackInSlot(EXTRA_SLOT).isEmpty() ||
                        !itemHandler.getStackInSlot(DYE_SLOT).isEmpty();

                boolean wandAlreadyPresent = !itemHandler.getStackInSlot(WAND_SLOT).isEmpty();

                if (otherSlotsFilled && !wandAlreadyPresent) {
                    return false;
                }

                if (otherSlotsFilled && wandAlreadyPresent) {
                    return true;
                }

            }
            return true;
        }
    };



    public IItemHandler getItemHandler() {
        return this.itemHandler;
    }

    public static final int CAP_SLOT = 0;
    public static final int HANDLE_SLOT = 1;
    public static final int EXTRA_SLOT = 2;
    public static final int DYE_SLOT = 3;
    public static final int WAND_SLOT = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
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
        return Component.translatable("block.energyandstuff.wand_customization_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new WandCustomizationTableMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void tick() {
        if (level != null && !level.isClientSide) {
            ItemStack currentWand = itemHandler.getStackInSlot(WAND_SLOT);

            if (previousWand.isEmpty() && !currentWand.isEmpty()) {
                uncraftWand();
            }

            if (!previousWand.isEmpty() && currentWand.isEmpty() && itemHandler.getStackInSlot(CAP_SLOT).isEmpty() && itemHandler.getStackInSlot(HANDLE_SLOT).isEmpty() && itemHandler.getStackInSlot(EXTRA_SLOT).isEmpty() && itemHandler.getStackInSlot(DYE_SLOT).isEmpty()) {
                if (level != null) {
                    Player player = level.getNearestPlayer(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 10, false);
                    if (player != null) {
                        level.playSound(null, player.blockPosition(), ModSounds.WAND_CREATION.get(), SoundSource.PLAYERS);
                        for (int i = 0; i < 10; i++) {
                            double xOffset = (Math.random() - 0.5) * 1.5;
                            double yOffset = Math.random() - 0.5;
                            double zOffset = (Math.random() - 0.5) * 1.5;
                            level.addParticle(ParticleTypes.CRIT, getBlockPos().getX() + xOffset, getBlockPos().getY() + yOffset, getBlockPos().getZ() + zOffset, 1, 1, 1);
                        }
                    }
                }
            }

            if (canCraft()) {
                craftItem();
            }
            checkWandIntegrity();

            previousWand = currentWand.copy();
        }

    }

    public SimpleContainer getInventory() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }

    private boolean canCraft() {
        boolean result = !itemHandler.getStackInSlot(CAP_SLOT).isEmpty()
                && !itemHandler.getStackInSlot(HANDLE_SLOT).isEmpty()
                && !itemHandler.getStackInSlot(EXTRA_SLOT).isEmpty();
        return result;
    }

    public void craftItem() {
        ItemStack cap = itemHandler.getStackInSlot(CAP_SLOT);
        ItemStack handle = itemHandler.getStackInSlot(HANDLE_SLOT);
        ItemStack extra = itemHandler.getStackInSlot(EXTRA_SLOT);
        ItemStack dye = itemHandler.getStackInSlot(DYE_SLOT);

        if (!cap.isEmpty() && !handle.isEmpty() && !extra.isEmpty()) {
            ItemStack wand = new ItemStack(ModItems.WAND.get());
            CompoundTag tag = wand.getOrCreateTag();

            List<TagKey<Item>> capTags = cap.getTags().toList();
            for (TagKey<Item> itemTag : capTags) {
                if (!itemTag.location().getPath().equals("wand_cap")) {
                    tag.putString("wand_cap", itemTag.location().getPath());
                    break;
                }
            }

            List<TagKey<Item>> handleTags = handle.getTags().toList();
            for (TagKey<Item> itemTag : handleTags) {
                if (!itemTag.location().getPath().equals("wand_handle")) {
                    tag.putString("wand_handle", itemTag.location().getPath());
                    break;
                }
            }

            List<TagKey<Item>> extraTags = extra.getTags().toList();
            for (TagKey<Item> itemTag : extraTags) {
                if (!itemTag.location().getPath().equals("wand_extra")) {
                    tag.putString("wand_extra", itemTag.location().getPath());
                    break;
                }
            }

            TagKey<Item> dyeTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "dyes"));
            if (dye.is(dyeTag)) {
                for (TagKey<Item> itemTag : dye.getTags().toList()) {
                    if (itemTag.location().getPath().startsWith("dyes/")) {
                        tag.putString("dye", itemTag.location().getPath().replace("dyes/", ""));
                        break;
                    }
                }
            }

            wand.setTag(tag);
            itemHandler.setStackInSlot(WAND_SLOT, wand);
        }
    }

    public void checkWandIntegrity() {
        ItemStack cap = itemHandler.getStackInSlot(CAP_SLOT);
        ItemStack handle = itemHandler.getStackInSlot(HANDLE_SLOT);
        ItemStack extra = itemHandler.getStackInSlot(EXTRA_SLOT);

        if (cap.isEmpty() || handle.isEmpty() || extra.isEmpty()) {
            itemHandler.setStackInSlot(WAND_SLOT, ItemStack.EMPTY);
            setChanged();
        }
    }

    public void uncraftWand() {
        ItemStack wand = itemHandler.getStackInSlot(WAND_SLOT);

        if (wand.isEmpty() || !wand.hasTag()) {
            return;
        }

        CompoundTag tag = wand.getTag();
        if (tag == null) {
            return;
        }

        String capId = tag.getString("wand_cap");
        String handleId = tag.getString("wand_handle");
        String extraId = tag.getString("wand_extra");


        if (capId.isEmpty() || handleId.isEmpty() || extraId.isEmpty()) {
            System.out.println("Ошибка: один из тегов пуст — cap = " + capId + ", handle = " + handleId + ", extra = " + extraId);
            return;
        }

        Item capItem = findItemByTag(capId);
        Item handleItem = findItemByTag(handleId);
        Item extraItem = findItemByTag(extraId);

        if (capItem != null && handleItem != null && extraItem != null) {
            itemHandler.setStackInSlot(CAP_SLOT, new ItemStack(capItem));
            itemHandler.setStackInSlot(HANDLE_SLOT, new ItemStack(handleItem));
            itemHandler.setStackInSlot(EXTRA_SLOT, new ItemStack(extraItem));

            setChanged();
        }
    }

    private Item findItemByTag(String tagId) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            ItemStack stack = new ItemStack(item);
            if (stack.getTags().map(t -> t.location().getPath()).anyMatch(tagId::equals)) {
                return item;
            }
        }
        return null;
    }

    private ItemStack previousWand = ItemStack.EMPTY;

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }
}
