package net.defge.energyandstuff.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class WandCustomizationTableScreen extends AbstractContainerScreen<WandCustomizationTableMenu> {
    private static final ResourceLocation WAND_CUSTOMIZATION_TABLE_TEXTURE = new ResourceLocation("energyandstuff", "textures/gui/wand_customization_table_gui.png");
    private Object itemRenderer;

    protected void init() {
        super.init();
        this.inventoryLabelY = 72;
        this.inventoryLabelX = 6;
        this.titleLabelY = 6;
        this.titleLabelX = 6;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTicks) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTicks);

        renderBg(pGuiGraphics);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (menu.getInventory().getItem(0).isEmpty()) {
            pGuiGraphics.blit(WAND_CUSTOMIZATION_TABLE_TEXTURE, x + 8, y + 54, 193, 0, 16, 16);  // Cap Slot
        }
        if (menu.getInventory().getItem(1).isEmpty()) {
            pGuiGraphics.blit(WAND_CUSTOMIZATION_TABLE_TEXTURE, x + 44, y + 54, 210, 0, 16, 16); // Handle Slot
        }
        if (menu.getInventory().getItem(2).isEmpty()) {
            pGuiGraphics.blit(WAND_CUSTOMIZATION_TABLE_TEXTURE, x + 80, y + 54, 227, 0, 16, 16); // Extra Slot
        }
        if (menu.getInventory().getItem(3).isEmpty()) {
            pGuiGraphics.blit(WAND_CUSTOMIZATION_TABLE_TEXTURE, x + 116, y + 54, 176, 17, 16, 16); // Dye Slot
        }
        if (menu.getInventory().getItem(4).isEmpty()) {
            pGuiGraphics.blit(WAND_CUSTOMIZATION_TABLE_TEXTURE, x + 62, y + 18, 176, 0, 16, 16);  // Wand Slot
        }

        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        ItemStack wandStack = menu.getSlot(40).getItem();
        if (!wandStack.isEmpty()){
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(x + 135,y + 28,0);
            pGuiGraphics.pose().scale(2.0F, 2.0F, 2.0F);
            //pGuiGraphics.pose().mulPose(new Quaternionf().rotateYXZ(rotationY * ((float) Math.PI / 180F), rotationX * ((float) Math.PI / 180F), rotationZ * ((float) Math.PI / 180F)));
            pGuiGraphics.renderItem(wandStack, 0, 0);
            pGuiGraphics.pose().popPose();
        }

    }

    private void renderBg(GuiGraphics pGuiGraphics) {
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, WAND_CUSTOMIZATION_TABLE_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(WAND_CUSTOMIZATION_TABLE_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    private final LivingEntity wandEntity;

    public WandCustomizationTableScreen(WandCustomizationTableMenu pMenu, Inventory pInventory, Component pTitle) {
        super(pMenu, pInventory, pTitle);
        this.wandEntity = EntityType.ARMOR_STAND.create(pInventory.player.level());
    }

    private float rotationX = 0.0F;
    private float rotationY = 0.0F;
    private float rotationZ = 0.0F;

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0) { // Левая кнопка мыши
            rotationY += dragX * 0.5F; // Вращение по горизонтали
            rotationX += dragY * 0.5F; // Вращение по вертикали
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }


}
