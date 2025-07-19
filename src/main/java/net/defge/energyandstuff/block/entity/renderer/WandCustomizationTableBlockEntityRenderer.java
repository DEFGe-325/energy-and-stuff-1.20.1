package net.defge.energyandstuff.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.defge.energyandstuff.block.entity.WandCustomizationTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class WandCustomizationTableBlockEntityRenderer implements BlockEntityRenderer<WandCustomizationTableBlockEntity> {
    public WandCustomizationTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(WandCustomizationTableBlockEntity wandCustomizationTableBlock, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack[] items = {
                wandCustomizationTableBlock.getItemHandler().getStackInSlot(4),
                wandCustomizationTableBlock.getItemHandler().getStackInSlot(0),
                wandCustomizationTableBlock.getItemHandler().getStackInSlot(1),
                wandCustomizationTableBlock.getItemHandler().getStackInSlot(2),
                wandCustomizationTableBlock.getItemHandler().getStackInSlot(3)
        };

        float[][] positions = {
                {0.5f, 0.70f, 0.5f},
                {0.25f, 0.70f, 0.25f},
                {0.75f, 0.70f, 0.25f},
                {0.25f, 0.70f, 0.75f},
                {0.75f, 0.70f, 0.75f},
        };

        for (int index = 0; index < items.length; index++) {
            ItemStack itemStack = items[index];
            if (!itemStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(positions[index][0], positions[index][1], positions[index][2]);
                poseStack.scale(0.25f, 0.25f, 0.25f);
                poseStack.mulPose(Axis.XP.rotationDegrees(270));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(wandCustomizationTableBlock.getLevel(), wandCustomizationTableBlock.getBlockPos()),
                        OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, wandCustomizationTableBlock.getLevel(), 1);
                poseStack.popPose();
            }
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blight = level.getBrightness(LightLayer.BLOCK, pos);
        int slight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blight, slight);
    }
}
