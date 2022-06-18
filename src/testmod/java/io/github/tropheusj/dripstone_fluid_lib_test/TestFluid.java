package io.github.tropheusj.dripstone_fluid_lib_test;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.FluidBlock;

import net.minecraft.block.PointedDripstoneBlock.DrippingFluid;

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

public abstract class TestFluid extends AbstractFluid implements DripstoneInteractingFluid {
	public String baseName;
	public int color;
	public net.minecraft.fluid.Fluid still;
	public net.minecraft.fluid.Fluid flowing;
	public Item bucket;
	public FluidBlock block;

	@Override
	public net.minecraft.fluid.Fluid getStill() {
		return still;
	}

	@Override
	public net.minecraft.fluid.Fluid getFlowing() {
		return flowing;
	}

	@Override
	public Item getBucketItem() {
		return bucket;
	}

	@Override
	protected int getFlowSpeed(WorldView worldView) {
		return 2;
	}

	@Override
	protected BlockState toBlockState(FluidState fluidState) {
		return block.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
	}

	@Override
	public float getFluidDripChance(World world, DrippingFluid fluid) {
		return WATER_DRIP_CHANCE;
	}

	@Override
	public boolean fillsCauldrons(BlockState state, World world, BlockPos cauldronPos) {
		return true;
	}

	@Override
	public BlockState getCauldronBlockState(BlockState state, World world, BlockPos cauldronPos) {
		return Blocks.DIRT.getDefaultState();
	}

	@Override
	public boolean growsDripstone(BlockState state) {
		return true;
	}

	@Override
	public int getParticleColor(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return color;
	}

	public static class Flowing extends TestFluid {
		public Flowing(String baseName, int color) {
			this.baseName = baseName;
			this.color = color;
			this.flowing = this;
		}

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

	public static class Still extends TestFluid {
		public Still(String baseName, int color) {
			this.baseName = baseName;
			this.color = color;
			this.still = this;
		}

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
