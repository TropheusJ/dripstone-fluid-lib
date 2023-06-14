package io.github.tropheusj.dripstone_fluid_lib.mixin;

import io.github.tropheusj.dripstone_fluid_lib.Constants;
import io.github.tropheusj.dripstone_fluid_lib.ParticleFactories.DrippingDripstoneFluidFactory;
import io.github.tropheusj.dripstone_fluid_lib.ParticleFactories.DripstoneFluidSplashFactory;
import io.github.tropheusj.dripstone_fluid_lib.ParticleFactories.FallingDripstoneFluidFactory;
import io.github.tropheusj.dripstone_fluid_lib.ParticleTypeSet;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.particle.ParticleManager;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow
	@Final
	public ParticleManager particleManager;

	@Inject(at = @At(value = "INVOKE", shift = At.Shift.BY, by = 2, target = "Lnet/minecraft/client/particle/ParticleManager;<init>(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/texture/TextureManager;)V"), method = "<init>")
	private void dripstone_fluid_lib$handleParticles(RunArgs args, CallbackInfo ci) {
		Constants.TO_REGISTER.forEach(fluid -> {
			ParticleTypeSet particles = Constants.FLUIDS_TO_PARTICLES.get(fluid);
			ParticleManagerAccessor access = (ParticleManagerAccessor) particleManager;
			access.callRegisterBlockLeakFactory(particles.hang(), new DrippingDripstoneFluidFactory(fluid));
			access.callRegisterBlockLeakFactory(particles.fall(), new FallingDripstoneFluidFactory(fluid));
			access.callRegisterFactory(particles.splash(), prov -> new DripstoneFluidSplashFactory(prov, fluid));
		});
	}
}
