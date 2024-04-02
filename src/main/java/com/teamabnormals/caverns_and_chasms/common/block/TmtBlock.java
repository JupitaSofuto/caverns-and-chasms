package com.teamabnormals.caverns_and_chasms.common.block;

import com.teamabnormals.caverns_and_chasms.common.entity.item.PrimedTmt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class TmtBlock extends TntBlock {

	public TmtBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
		if (!level.isClientSide) {
			PrimedTmt primedtmt = new PrimedTmt(level, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosion.getIndirectSourceEntity());
			int i = primedtmt.getFuse();
			primedtmt.setFuse((short) (level.random.nextInt(i / 4) + i / 8));
			level.addFreshEntity(primedtmt);
		}
	}

	@Override
	public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
		explode(world, pos, igniter);
	}

	public static void explode(Level level, BlockPos pos) {
		explode(level, pos, null);
	}

	public static void explode(Level level, BlockPos pos, @Nullable LivingEntity igniter) {
		if (!level.isClientSide) {
			PrimedTmt primedtmt = new PrimedTmt(level, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter);
			level.addFreshEntity(primedtmt);
			level.playSound(null, primedtmt.getX(), primedtmt.getY(), primedtmt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
		}
	}
}
