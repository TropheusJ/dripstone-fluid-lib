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
public abstract class CauldronBlockMixin {
	@Inject(method = "fillFromDripstone", at = @At("HEAD"), cancellable = true)
	private void dripstone_fluid_lib$customFluidsFillCauldrons(BlockState state, World world, BlockPos pos, Fluid fluid, CallbackInfo ci) {
		if (fluid instanceof DripstoneInteractingFluid interactingFluid) {
			if (interactingFluid.fillsCauldrons(state, world, pos)) {
				BlockState newState = interactingFluid.getCauldronBlockState(state, world, pos);
				world.setBlockState(pos, newState);
				world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newState));
				world.syncWorldEvent(interactingFluid.getFluidDripWorldEvent(state, world, pos), pos, 0);
				ci.cancel();
			}
		}
	}
}
