package io.github.tropheusj.dripstone_fluid_lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.tropheusj.dripstone_fluid_lib.DripstoneFluidLib;
import io.github.tropheusj.dripstone_fluid_lib.DripstoneInteractingFluid;
import io.github.tropheusj.dripstone_fluid_lib.Triplet;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(Registry.class)
public class RegistryMixin {
	@Inject(at = @At("HEAD"), method = "register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;")
	private static <V, T extends V> void dripstone_fluid_lib$register(Registry<V> registry, Identifier id, T entry, CallbackInfoReturnable<T> cir) {
		if (entry instanceof DripstoneInteractingFluid interactingFluid) {
			DefaultParticleType hang = Registry.register(Registry.PARTICLE_TYPE,
					new Identifier(id.getNamespace(), id.getPath() + "_dripstone_lib_particle_type_hang"),
					FabricParticleTypes.simple());
			DefaultParticleType fall = Registry.register(Registry.PARTICLE_TYPE,
					new Identifier(id.getNamespace(), id.getPath() + "_dripstone_lib_particle_type_fall"),
					FabricParticleTypes.simple());
			DefaultParticleType splash = Registry.register(Registry.PARTICLE_TYPE,
					new Identifier(id.getNamespace(), id.getPath() + "_dripstone_lib_particle_type_splash"),
					FabricParticleTypes.simple());
			DripstoneFluidLib.FLUIDS_TO_PARTICLES.put(interactingFluid, new Triplet<>(hang, fall, splash));
		}
	}
}
