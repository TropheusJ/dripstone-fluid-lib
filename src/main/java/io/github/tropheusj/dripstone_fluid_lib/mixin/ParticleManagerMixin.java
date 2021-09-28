package io.github.tropheusj.dripstone_fluid_lib.mixin;

import java.util.List;
import java.util.Map;

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
public class ParticleManagerMixin {
	@Unique
	private static final List<Identifier> DRIP_HANG = ImmutableList.of(new Identifier("minecraft", "particle/drip_hang"));
	@Unique
	private static final List<Identifier> DRIP_FALL = ImmutableList.of(new Identifier("minecraft", "particle/drip_fall"));
	@Unique
	private static final List<Identifier> SPLASH = ImmutableList.of(
			new Identifier("dripstone_fluid_lib", "particle/splash_0"),
			new Identifier("dripstone_fluid_lib", "particle/splash_1"),
			new Identifier("dripstone_fluid_lib", "particle/splash_2"),
			new Identifier("dripstone_fluid_lib", "particle/splash_3")
	);

	@Inject(at = @At("HEAD"), method = "loadTextureList", cancellable = true)
	private void loadTextureList(ResourceManager resourceManager, Identifier id, Map<Identifier, List<Identifier>> result, CallbackInfo ci) {
		if (id.getPath().endsWith("_dripstone_lib_particle_type_hang")) {
			result.put(id, DRIP_HANG);
			ci.cancel();
		} else if (id.getPath().endsWith("_dripstone_lib_particle_type_fall")) {
			result.put(id, DRIP_FALL);
			ci.cancel();
		} else if (id.getPath().endsWith("_dripstone_lib_particle_type_splash")) {
			result.put(id, SPLASH);
			ci.cancel();
		}
	}
}
