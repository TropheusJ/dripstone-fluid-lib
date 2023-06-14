package io.github.tropheusj.dripstone_fluid_lib.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import io.github.tropheusj.dripstone_fluid_lib.Constants;

import io.github.tropheusj.dripstone_fluid_lib.Shenanigans;
import net.minecraft.registry.Registries;

import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleManager;

import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
	// last lambda in reload
	@Redirect(method = "method_45766",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"
			)
	)
	private void dripstone_fluid_lib$addReloadResults(List<Object> reloadResults, Consumer<Object> consumer) {
		List<Object> modifiableResults = new ArrayList<>(reloadResults);
		Registries.PARTICLE_TYPE.streamEntries().forEach(holder -> {
			Identifier id = holder.registryKey().getValue();
			String path = id.getPath();
			List<Identifier> sprites = null;
			if (path.endsWith("_dripstone_lib_particle_type_hang")) {
				sprites = Constants.DRIP_HANG;
			} else if (path.endsWith("_dripstone_lib_particle_type_fall")) {
				sprites = Constants.DRIP_FALL;
			} else if (path.endsWith("_dripstone_lib_particle_type_splash")) {
				sprites = Constants.SPLASH;
			}
			if (sprites != null) {
				modifiableResults.add(Shenanigans.createReloadResult(id, sprites));
			}
		});
		modifiableResults.forEach(consumer);
	}
}
