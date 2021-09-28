package io.github.tropheusj.dripstone_fluid_lib;

import io.github.tropheusj.dripstone_fluid_lib.mixin.WaterSplashParticle$SplashFactoryAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class ParticleFactories {
	public static class DrippingDripstoneFluidFactory extends BlockLeakParticle.FallingDripstoneWaterFactory {
		private final DripstoneInteractingFluid fluid;

		public DrippingDripstoneFluidFactory(SpriteProvider spriteProvider, DripstoneInteractingFluid fluid) {
			super(spriteProvider);
			this.fluid = fluid;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			BlockLeakParticle particle = new BlockLeakParticle.Dripping(clientWorld, x, y, z, Fluids.WATER, DripstoneFluidLib.FLUIDS_TO_PARTICLES.get(fluid).getB()); // falling
			particle.setSprite(this.spriteProvider);
			int color = fluid.getParticleColor(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			float r = (color >> 16 & 255) / 255f;
			float g = (color >> 8 & 255) / 255f;
			float b = (color & 255) / 255f;
			particle.setColor(r, g, b);
			return particle;
		}
	}

	public static class FallingDripstoneFluidFactory extends BlockLeakParticle.DripstoneLavaSplashFactory {
		private final DripstoneInteractingFluid fluid;

		public FallingDripstoneFluidFactory(SpriteProvider spriteProvider, DripstoneInteractingFluid fluid) {
			super(spriteProvider);
			this.fluid = fluid;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			BlockLeakParticle particle = new BlockLeakParticle.DripstoneLavaDrip(clientWorld, x, y, z, Fluids.WATER,
					DripstoneFluidLib.FLUIDS_TO_PARTICLES.get(fluid).getC()); // splash
			particle.setSprite(this.spriteProvider);
			int color = fluid.getParticleColor(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			float r = (color >> 16 & 255) / 255f;
			float g = (color >> 8 & 255) / 255f;
			float b = (color & 255) / 255f;
			particle.setColor(r, g, b);
			return particle;
		}
	}

	public static class DripstoneFluidSplashFactory extends WaterSplashParticle.SplashFactory {
		private final DripstoneInteractingFluid fluid;

		public DripstoneFluidSplashFactory(SpriteProvider spriteProvider, DripstoneInteractingFluid fluid) {
			super(spriteProvider);
			this.fluid = fluid;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			WaterSplashParticle particle = new DripstoneFluidParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			particle.setSprite(((WaterSplashParticle$SplashFactoryAccessor) this).getSpriteProvider());
			int color = fluid.getParticleColor(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			float r = (color >> 16 & 255) / 255f;
			float g = (color >> 8 & 255) / 255f;
			float b = (color & 255) / 255f;
			particle.setColor(r, g, b);
			return particle;
		}
	}
}
