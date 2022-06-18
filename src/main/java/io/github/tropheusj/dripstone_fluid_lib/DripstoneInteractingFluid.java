package io.github.tropheusj.dripstone_fluid_lib;

import net.minecraft.block.PointedDripstoneBlock.DrippingFluid;

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
	 * The chance for this fluid to drip from dripstone, between 0 and 1.
	 * @see DripstoneInteractingFluid#WATER_DRIP_CHANCE
	 * @see DripstoneInteractingFluid#LAVA_DRIP_CHANCE
	 */
	float getFluidDripChance(World world, DrippingFluid fluid);

	/**
	 * @return true if this fluid should cause pointed dripstone to grow downwards when placed above
	 */
	boolean growsDripstone(BlockState state);

	/**
	 * The color, in integer form, of the particle created by this fluid.
	 * tip: use hex codes! ex. 0xFFFFFF for white.
	 */
	int getParticleColor(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

	/**
	 * @return true if this fluid should drip into and full cauldrons.
	 */
	boolean fillsCauldrons(BlockState state, World world, BlockPos cauldronPos);

	/**
	 * The blockstate to set when dripstone drips this fluid into a {@link CauldronBlock}, filling it.
	 * Only called if {@link DripstoneInteractingFluid#fillsCauldrons(BlockState, World, BlockPos)} returns true.
	 * Remember to implement {@link AbstractCauldronBlock#fillFromDripstone} if you have a custom cauldron block.
	 */
	BlockState getCauldronBlockState(BlockState state, World world, BlockPos cauldronPos);

	/**
	 * The world event triggered when a cauldron is filled with this fluid
	 * @see WorldEvents#POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON
	 * @see WorldEvents#POINTED_DRIPSTONE_DRIPS_LAVA_INTO_CAULDRON
	 */
	default int getFluidDripWorldEvent(BlockState state, World world, BlockPos cauldronPos) {
		return WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON;
	}
}
