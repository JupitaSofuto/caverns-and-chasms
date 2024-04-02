package com.teamabnormals.caverns_and_chasms.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamabnormals.caverns_and_chasms.client.model.CopperGolemModel;
import com.teamabnormals.caverns_and_chasms.common.entity.decoration.OxidizedCopperGolem;
import com.teamabnormals.caverns_and_chasms.core.CavernsAndChasms;
import com.teamabnormals.caverns_and_chasms.core.other.CCModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OxidizedCopperGolemRenderer extends LivingEntityRenderer<OxidizedCopperGolem, CopperGolemModel<OxidizedCopperGolem>> {
	private static final ResourceLocation OXIDIZED_GOLEM_TEXTURE = new ResourceLocation(CavernsAndChasms.MOD_ID, "textures/entity/copper_golem/copper_golem_oxidized.png");
	private static final ResourceLocation DAMAGED_OXIDIZED_GOLEM_TEXTURE = new ResourceLocation(CavernsAndChasms.MOD_ID, "textures/entity/copper_golem/copper_golem_oxidized_damaged.png");

	public OxidizedCopperGolemRenderer(EntityRendererProvider.Context context) {
		super(context, new CopperGolemModel<>(context.bakeLayer(CCModelLayers.COPPER_GOLEM)), 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(OxidizedCopperGolem copperGolem) {
		return copperGolem.isDamaged() ? DAMAGED_OXIDIZED_GOLEM_TEXTURE : OXIDIZED_GOLEM_TEXTURE;
	}

	@Override
	protected void setupRotations(OxidizedCopperGolem copperGolem, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
		float f = (float) (copperGolem.level().getGameTime() - copperGolem.lastHit) + partialTicks;
		if (f < 5.0F) {
			matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(f / 1.5F * (float) Math.PI) * 3.0F));
		}
	}

	@Override
	protected boolean shouldShowName(OxidizedCopperGolem Override) {
		return super.shouldShowName(Override) && (Override.shouldShowName() || Override.hasCustomName() && Override == this.entityRenderDispatcher.crosshairPickEntity);
	}
}