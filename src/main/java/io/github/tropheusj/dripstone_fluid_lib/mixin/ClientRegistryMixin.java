package io.github.tropheusj.dripstone_fluid_lib.mixin;

import io.github.tropheusj.dripstone_fluid_lib.Constants;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.tropheusj.dripstone_fluid_lib.DripstoneInteractingFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
@Mixin(Registry.class)
public class ClientRegistryMixin {
	@Inject(at = @At("RETURN"), method = "register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;")
	private static <V, T extends V> void dripstone_fluid_lib$clientRegister(Registry<V> registry, Identifier id, T entry, CallbackInfoReturnable<T> cir) {
		if (entry instanceof DripstoneInteractingFluid interactingFluid) {
			Constants.TO_REGISTER.add(interactingFluid);
		}
	}
}
