package com.starvinelonya.slowmadism.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.starvinelonya.slowmadism.block.entity.StoneMillTile;
import com.starvinelonya.slowmadism.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import org.jetbrains.annotations.NotNull;

/**
 * StoneMillTileEntityRender
 *
 * @author Slowmadism
 * @date 2025/04/01
 */
public class StoneMillTileEntityRender extends TileEntityRenderer<StoneMillTile> {

    public StoneMillTileEntityRender(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@NotNull StoneMillTile tileEntityIn, float partialTicks, @NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        // 推入矩阵堆栈，保存当前变换状态。
        matrixStackIn.push();
        // 获取石磨块的旋转角度。
        int rotation = tileEntityIn.getRotation();
        // 将渲染原点平移到石磨块的中心。
        matrixStackIn.translate(0.5, 0.5, 0.5);
        // 根据旋转角度旋转矩阵堆栈。
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation * 45));

        // 获取 Minecraft 的 ItemRenderer 实例。
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        // 创建 StoneMillBlockTile 的上部物品堆栈。
        ItemStack stack = new ItemStack(ItemRegistry.STONE_MILL_UPPER.get());
        // 使用 ItemRenderer 渲染上部物品堆栈。
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
        // 弹出矩阵堆栈，恢复之前的变换状态。
        matrixStackIn.pop();
    }

}
