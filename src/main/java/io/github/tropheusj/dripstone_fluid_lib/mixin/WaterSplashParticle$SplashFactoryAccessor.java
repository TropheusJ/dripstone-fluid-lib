package io.github.tropheusj.dripstone_fluid_lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSplashParticle;

@Environment(EnvType.CLIENT)
@Mixin(WaterSplashParticle.SplashFactory.class)
public interface WaterSplashParticle$SplashFactoryAccessor {
	@Accessor("spriteProvider")
	SpriteProvider dripstone_fluid_lib$spriteProvider();
}
