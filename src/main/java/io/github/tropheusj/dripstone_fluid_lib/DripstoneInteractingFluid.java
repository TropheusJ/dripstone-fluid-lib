package io.github.tropheusj.dripstone_fluid_lib;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public interface DripstoneInteractingFluid {
	float WATER_DRIP_CHANCE = 0.17578125f;
	float LAVA_DRIP_CHANCE = 0.05859375f;

	/**
	 * @see DripstoneInteractingFluid#WATER_DRIP_CHANCE
	 * @see DripstoneInteractingFluid#LAVA_DRIP_CHANCE
	 */
	float getFluidDripChance(BlockState state, World world, BlockPos pos);

	boolean growsDripstone(BlockState state);

	/**
	 * The color, in integer form, of the particle created by this fluid.
	 * tip: use hex codes! ex. 0xFFFFFF for white.
	 */
	int getParticleColor(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

	/**
	 * The blockstate to set when dripstone drips this fluid into a {@link CauldronBlock}, filling it.
	 * returning null will prevent cauldron filling.
	 * Remember to implement {@link AbstractCauldronBlock#fillFromDripstone} if you have a custom cauldron block.
	 */
	@Nullable
	BlockState getCauldronBlockState(BlockState state, World world, BlockPos cauldronPos);

	/**
	 * The world event triggered when a cauldron is filled with this fluid
	 * @see WorldEvents#POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON
	 * @see WorldEvents#POINTED_DRIPSTONE_DRIPS_LAVA_INTO_CAULDRON
	 */
	int getFluidDripWorldEvent(BlockState state, World world, BlockPos cauldronPos);
}
