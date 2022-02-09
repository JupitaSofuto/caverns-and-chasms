package com.teamabnormals.caverns_and_chasms.common.block;

import java.util.Random;

import com.teamabnormals.blueprint.core.util.NetworkUtil;
import com.teamabnormals.caverns_and_chasms.core.CavernsAndChasms;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public interface FragileBlock {

	default void breakNeighbors(Level level, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			BlockPos blockpos = pos.relative(direction);
			Block block = level.getBlockState(blockpos).getBlock();
			if (block instanceof FragileBlock) {
				level.scheduleTick(blockpos, block, 4 + level.getRandom().nextInt(4));
			}
		}
	}

	default void crack(Level level, BlockState state, BlockPos pos, Random random, String particlestring) {
		double d0 = pos.getX() + random.nextDouble() * 0.8D + 0.1D;
		double d1 = pos.getY() + random.nextDouble() * 0.8D + 0.1D;
		double d2 = pos.getZ() + random.nextDouble() * 0.8D + 0.1D;
		double d3 = random.nextGaussian() * 0.04D;
		double d4 = random.nextGaussian() * 0.04D;
		double d5 = random.nextGaussian() * 0.04D;
		NetworkUtil.spawnParticle(CavernsAndChasms.MOD_ID + ":fragile_stone_dust", d0, d1, d2, d3, d4, d5);

		for(int x = 0; x < 3; ++x) {
			double d6 = pos.getX() + random.nextDouble() * 0.8D + 0.1D;
			double d7 = pos.getY() + random.nextDouble() * 0.8D + 0.1D;
			double d8 = pos.getZ() + random.nextDouble() * 0.8D + 0.1D;
			double d9 = ((double) random.nextFloat() - 0.5D) * 0.02D;
			double d10 = ((double) random.nextFloat() - 0.5D) * 0.02D;
			NetworkUtil.spawnParticle(CavernsAndChasms.MOD_ID + ":" + particlestring, d6, d7, d8, d9, -0.4D, d10);
		}

		SoundType soundtype = state.getSoundType(level, pos, null);
		level.playSound(null, pos, soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

		level.removeBlock(pos, true);
	}

	default boolean shouldBreakNeighbors(Player player, ItemStack stack) {
		return !player.isCreative() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0;
	}
}