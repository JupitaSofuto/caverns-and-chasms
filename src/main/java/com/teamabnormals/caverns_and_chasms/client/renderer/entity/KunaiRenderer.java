package com.teamabnormals.caverns_and_chasms.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KunaiRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
	private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	private final float scale;
	private final boolean fullBright;

	public KunaiRenderer(EntityRendererProvider.Context context, float p_i226035_3_, boolean p_i226035_4_) {
		super(context);
		this.scale = p_i226035_3_;
		this.fullBright = p_i226035_4_;
	}

	public KunaiRenderer(EntityRendererProvider.Context context) {
		this(context, 1.0F, false);
	}

	@Override
	protected int getBlockLightLevel(T entityIn, BlockPos partialTicks) {
		return this.fullBright ? 15 : super.getBlockLightLevel(entityIn, partialTicks);
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		float testScale = 1.5F;
		matrixStackIn.scale(this.scale, this.scale, this.scale);
		matrixStackIn.scale(testScale, testScale, testScale);

		matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
		matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		matrixStackIn.mulPose(Axis.ZP.rotationDegrees(-135.0F));

		matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
		matrixStackIn.translate(0.0F, -0.175F, 0.0F);

		this.itemRenderer.renderStatic(entityIn.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entityIn.level(), entityIn.getId());
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(Entity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
