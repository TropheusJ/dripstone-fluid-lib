package io.github.tropheusj.dripstone_fluid_lib.mixin;

import io.github.tropheusj.dripstone_fluid_lib.DripstoneInteractingFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.world.event.GameEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {
	@Inject(at = @At("HEAD"), method = "fillFromDripstone")
	protected void dripstone_fluid_lib$fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid, CallbackInfo ci) {
		if (fluid instanceof DripstoneInteractingFluid interactingFluid) {
			BlockState cauldronBlock = interactingFluid.getCauldronBlockState(state, world, pos);
			if (cauldronBlock != null) {
				world.setBlockState(pos, cauldronBlock);
				world.syncWorldEvent(interactingFluid.getFluidDripWorldEvent(state, world, pos), pos, 0);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}
		}
	}
}
