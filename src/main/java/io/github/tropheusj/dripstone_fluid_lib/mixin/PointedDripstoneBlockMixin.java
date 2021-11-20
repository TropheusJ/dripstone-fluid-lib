package io.github.tropheusj.dripstone_fluid_lib.mixin;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.annotations.VisibleForTesting;

import io.github.tropheusj.dripstone_fluid_lib.DripstoneFluidLib;
import io.github.tropheusj.dripstone_fluid_lib.DripstoneInteractingFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin {
	@Shadow
	private static Optional<Fluid> getFluid(World world, BlockPos pos, BlockState state) {
		throw new RuntimeException("Mixin application failed!");
	}

	@Shadow
	private static boolean isHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos) {
		throw new RuntimeException("Mixin application failed!");
	}

	@Shadow
	@Nullable
	private static BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
		throw new RuntimeException("Mixin application failed!");
	}

	@Shadow
	private static Fluid getDripFluid(World world, Fluid fluid) {
		throw new RuntimeException("Mixin application failed!");
	}

	@Shadow
	@Nullable
	private static BlockPos getCauldronPos(World world, BlockPos pos, Fluid fluid) {
		throw new RuntimeException("Mixin application failed!");
	}

	/**
	 * @reason mojank
	 * @author Tropheus Jay
	 */
	@Overwrite
	public static Fluid getDripFluid(World world, BlockPos pos) {
		Optional<Fluid> dripFluid = getFluid(world, pos, world.getBlockState(pos));
		return dripFluid.orElse(Fluids.EMPTY);
	}

	/**
	 * @reason mojank
	 * @author Tropheus Jay
	 */
	@Overwrite
	@VisibleForTesting
	public static void dripTick(BlockState state, ServerWorld world, BlockPos pos, float dripChance) {
		Fluid fluid = getDripFluid(world, pos);
		float fluidDripChance;
		if (fluid instanceof DripstoneInteractingFluid dripFluid) {
			fluidDripChance = dripFluid.getFluidDripChance(state, world, pos);
		} else if (fluid == Fluids.WATER) {
			fluidDripChance = DripstoneInteractingFluid.WATER_DRIP_CHANCE;
		} else if (fluid == Fluids.LAVA) {
			fluidDripChance = DripstoneInteractingFluid.LAVA_DRIP_CHANCE;
		} else return;

		if (dripChance <= fluidDripChance) {
			if (isHeldByPointedDripstone(state, world, pos)) {
				if (!(dripChance >= fluidDripChance)) {
					BlockPos blockPos = getTipPos(state, world, pos, 11, false);
					if (blockPos != null) {
						BlockPos blockPos2 = getCauldronPos(world, blockPos, fluid);
						if (blockPos2 != null) {
							world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS, blockPos, 0);
							int i = blockPos.getY() - blockPos2.getY();
							int j = 50 + i;
							BlockState blockState = world.getBlockState(blockPos2);
							world.createAndScheduleBlockTick(blockPos2, blockState.getBlock(), j);
						}
					}
				}
			}
		}
	}

	/**
	 * @reason get particle effect for other fluids
	 * @author Tropheus Jay
	 */
	@Overwrite
	private static void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid) {
		Vec3d modelOffset = state.getModelOffset(world, pos);
		double x = pos.getX() + 0.5 + modelOffset.x;
		double y = ((pos.getY() + 1) - 0.6875F) - 0.0625;
		double z = pos.getZ() + 0.5 + modelOffset.z;
		Fluid dripFluid = getDripFluid(world, fluid);
		ParticleEffect particleEffect;
		if (dripFluid instanceof DripstoneInteractingFluid interactingFluid) {
			particleEffect = DripstoneFluidLib.FLUIDS_TO_PARTICLES.get(interactingFluid).getA(); // hang
		} else {
			particleEffect = dripFluid.isIn(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
		}
		world.addParticle(particleEffect, x, y, z, 0.0, 0.0, 0.0);
	}

	/**
	 * @reason allow fluids other than water to grow dripstone
	 * @author Tropheus Jay
	 */
	@Overwrite
	private static boolean canGrow(BlockState dripstoneBlockState, BlockState fluidState) {
		Fluid fluid = fluidState.getFluidState().getFluid();
		boolean growsDripstone = fluidState.isOf(Blocks.WATER);
		if (fluid instanceof DripstoneInteractingFluid interactingFluid) {
			growsDripstone = interactingFluid.growsDripstone(fluidState);
		}

		return dripstoneBlockState.isOf(Blocks.DRIPSTONE_BLOCK) && growsDripstone && fluidState.getFluidState().isStill();
	}

	// isFluidLiquid lambda in randomDisplayTick
	@Redirect(method = "method_33270", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/PointedDripstoneBlock;isFluidLiquid(Lnet/minecraft/fluid/Fluid;)Z"))
	private static boolean dripstone_fluid_lib$redirectFluidCheck(Fluid fluid) {
		return fluid != null;
	}
}
