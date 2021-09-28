package io.github.tropheusj.dripstone_fluid_lib_test;

import static io.github.tropheusj.dripstone_fluid_lib_test.DripstoneFluidLibTestMod.BUCKET;
import static io.github.tropheusj.dripstone_fluid_lib_test.DripstoneFluidLibTestMod.FLOWING_FLUID;
import static io.github.tropheusj.dripstone_fluid_lib_test.DripstoneFluidLibTestMod.FLUID;
import static io.github.tropheusj.dripstone_fluid_lib_test.DripstoneFluidLibTestMod.STILL_FLUID;

import org.jetbrains.annotations.Nullable;

import io.github.tropheusj.dripstone_fluid_lib.DripstoneInteractingFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

public abstract class Fluid extends AbstractFluid implements DripstoneInteractingFluid {
	@Override
	public net.minecraft.fluid.Fluid getStill() {
		return STILL_FLUID;
	}

	@Override
	public net.minecraft.fluid.Fluid getFlowing() {
		return FLOWING_FLUID;
	}

	@Override
	public Item getBucketItem() {
		return BUCKET;
	}

	@Override
	protected int getFlowSpeed(WorldView worldView) {
		return 2;
	}

	@Override
	protected BlockState toBlockState(FluidState fluidState) {
		return FLUID.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
	}

	@Override
	public float getFluidDripChance(BlockState state, World world, BlockPos pos) {
		return WATER_DRIP_CHANCE;
	}

	@Override
	public @Nullable BlockState getCauldronBlockState(BlockState state, World world, BlockPos cauldronPos) {
		return Blocks.CAULDRON.getDefaultState();
	}

	@Override
	public int getFluidDripWorldEvent(BlockState state, World world, BlockPos cauldronPos) {
		return WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON;
	}

	@Override
	public boolean growsDripstone(BlockState state) {
		return true;
	}

	@Override
	public int getParticleColor(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return 0x00A86B;
	}

	public static class Flowing extends Fluid {
		@Override
		protected void appendProperties(StateManager.Builder<net.minecraft.fluid.Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getLevel(FluidState fluidState) {
			return fluidState.get(LEVEL);
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return false;
		}
	}

	public static class Still extends Fluid {
		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}
	}
}
