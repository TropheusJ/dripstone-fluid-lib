package io.github.tropheusj.dripstone_fluid_lib.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.tropheusj.dripstone_fluid_lib.DripstoneFluidLib;
import io.github.tropheusj.dripstone_fluid_lib.DripstoneFluidLibClient;
import io.github.tropheusj.dripstone_fluid_lib.ParticleFactories;
import io.github.tropheusj.dripstone_fluid_lib.Triplet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	@Final
	public ParticleManager particleManager;

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BY, by = 2, target = "Lnet/minecraft/client/particle/ParticleManager;<init>(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/texture/TextureManager;)V"), method = "<init>")
	private void dripstone_fluid_lib$handleParticles(RunArgs args, CallbackInfo ci) {
		DripstoneFluidLibClient.TO_REGISTER.forEach(fluid -> {
			Triplet<DefaultParticleType, DefaultParticleType, DefaultParticleType> particles = DripstoneFluidLib.FLUIDS_TO_PARTICLES.get(fluid);
			((ParticleManagerAccessor) particleManager).invokeRegisterFactory(particles.getA(),
					spriteProvider -> new ParticleFactories.DrippingDripstoneFluidFactory(spriteProvider, fluid));
			((ParticleManagerAccessor) particleManager).invokeRegisterFactory(particles.getB(),
					spriteProvider -> new ParticleFactories.FallingDripstoneFluidFactory(spriteProvider, fluid));
			((ParticleManagerAccessor) particleManager).invokeRegisterFactory(particles.getC(),
					spriteProvider -> new ParticleFactories.DripstoneFluidSplashFactory(spriteProvider, fluid));
		});
	}
}
