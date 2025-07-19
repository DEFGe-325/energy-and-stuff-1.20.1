package net.defge.energyandstuff.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.defge.energyandstuff.EnergyAndStuff;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class FuelGeneratorScreen extends AbstractContainerScreen<FuelGeneratorMenu> {
    private static final ResourceLocation FUEL_GENERATOR_TEXTURE =
            new ResourceLocation(EnergyAndStuff.MOD_ID, "textures/gui/fuel_generator_gui.png");

    public FuelGeneratorScreen(FuelGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, Component.translatable("block.energyandstuff.fuel_generator"));
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 72;
        this.inventoryLabelX = 6;
        this.titleLabelY = 6;
        this.titleLabelX = 6;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, FUEL_GENERATOR_TEXTURE);
        int energy = menu.getEnergyStored();
        int maxEnergy = menu.getMaxEnergyStored();
        int energyHeight = (int) ((energy / (float) maxEnergy) * 41);
        int textureYOffset = 41 - energyHeight;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(FUEL_GENERATOR_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        pGuiGraphics.blit(FUEL_GENERATOR_TEXTURE, x + 135, y + 23 + textureYOffset, 176, textureYOffset, 13, energyHeight);


        int maxBurnTime = menu.getMaxBurnTime();
        int burnTime = menu.getBurnTime();
        int burnTimeHeight = Math.max((int) ((burnTime / (float) maxBurnTime) * 13), 1);
        int burnTimeYOffset = 13 - burnTimeHeight;
        if (burnTime > 0) {
            pGuiGraphics.blit(FUEL_GENERATOR_TEXTURE, x + 80, y + 37 + burnTimeYOffset, 192, burnTimeYOffset, 14, burnTimeHeight);
        }


    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int barX = x + 135;
        int barY = y + 23;
        int barWidth = 13;
        int barHeight = 41;

        if (pMouseX >= barX && pMouseX <= barX + barWidth && pMouseY >= barY && pMouseY <= barY + barHeight) {
            List<Component> tooltip = List.of(Component.translatable("tooltip.energy", menu.getEnergyStored(), menu.getMaxEnergyStored()));
            pGuiGraphics.renderTooltip(font, tooltip, Optional.empty(), pMouseX, pMouseY);
        }
    }
}
