package io.github.tropheusj.dripstone_fluid_lib.mixin;

import java.util.List;
import java.util.Map;

import io.github.tropheusj.dripstone_fluid_lib.Constants;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
	@Inject(at = @At("HEAD"), method = "loadTextureList", cancellable = true)
	private void dripstone_fluid_lib$loadTextureList(ResourceManager resourceManager, Identifier id, Map<Identifier, List<Identifier>> result, CallbackInfo ci) {
		if (id.getPath().endsWith("_dripstone_lib_particle_type_hang")) {
			result.put(id, Constants.DRIP_HANG);
			ci.cancel();
		} else if (id.getPath().endsWith("_dripstone_lib_particle_type_fall")) {
			result.put(id, Constants.DRIP_FALL);
			ci.cancel();
		} else if (id.getPath().endsWith("_dripstone_lib_particle_type_splash")) {
			result.put(id, Constants.SPLASH);
			ci.cancel();
		}
	}
}
