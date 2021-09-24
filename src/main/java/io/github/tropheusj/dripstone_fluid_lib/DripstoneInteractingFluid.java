package io.github.tropheusj.dripstone_fluid_lib;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.particle.ParticleEffect;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import org.jetbrains.annotations.Nullable;

public interface DripstoneInteractingFluid {
	float WATER_DRIP_CHANCE = 0.17578125F;
	float LAVA_DRIP_CHANCE = 0.05859375F;

	float getFluidDripChance(BlockState state, World world, BlockPos pos);
	ParticleEffect getParticleEffect(World world, BlockPos pos, BlockState state);
	boolean growsDripstone(BlockState state);

	/**
	 * The blockstate to set when dripstone drips this fluid into a {@link CauldronBlock}, filling it.
	 * returning null will prevent cauldron filling.
	 */
	@Nullable
	BlockState getCauldronBlockState(BlockState state, World world, BlockPos cauldronPos);

	/**
	 * The world event triggered when a cauldron is filled with this fluid
	 * Use {@link WorldEvents#POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON} or
	 * {@link WorldEvents#POINTED_DRIPSTONE_DRIPS_LAVA_INTO_CAULDRON} if you don't want to make your own
	 */
	int getFluidDripWorldEvent(BlockState state, World world, BlockPos cauldronPos);
}
