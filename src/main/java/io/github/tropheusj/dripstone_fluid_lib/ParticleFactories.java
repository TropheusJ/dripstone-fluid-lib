package io.github.tropheusj.dripstone_fluid_lib;

import io.github.tropheusj.dripstone_fluid_lib.mixin.WaterSplashParticle$SplashFactoryAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class ParticleFactories {
	/**
	 * {@link BlockLeakParticle#createDrippingDripstoneWater(DefaultParticleType, ClientWorld, double, double, double, double, double, double)}
	 */
	public record DrippingDripstoneFluidFactory(DripstoneInteractingFluid fluid)
			implements ParticleFactory.BlockLeakParticleFactory<DefaultParticleType> {
		@Override
		public SpriteBillboardParticle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld,
													  double x, double y, double z,
													  double velocityX, double velocityY, double velocityZ) {
			BlockLeakParticle particle = new BlockLeakParticle.Dripping(
					clientWorld, x, y, z, (Fluid) fluid, Constants.FLUIDS_TO_PARTICLES.get(fluid).fall()
			);
			int color = fluid.getParticleColor(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			float r = (color >> 16 & 255) / 255f;
			float g = (color >> 8 & 255) / 255f;
			float b = (color & 255) / 255f;
			particle.setColor(r, g, b);
			return particle;
		}
	}

	public record FallingDripstoneFluidFactory(DripstoneInteractingFluid fluid)
			implements ParticleFactory.BlockLeakParticleFactory<DefaultParticleType> {
		@Override
		public SpriteBillboardParticle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld,
													  double x, double y, double z,
													  double velocityX, double velocityY, double velocityZ) {
			BlockLeakParticle particle = new BlockLeakParticle.DripstoneLavaDrip(
					clientWorld, x, y, z, (Fluid) fluid, Constants.FLUIDS_TO_PARTICLES.get(fluid).splash()
			);
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
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld,
									   double x, double y, double z,
									   double velocityX, double velocityY, double velocityZ) {
			WaterSplashParticle particle = new DripstoneFluidParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			particle.setSprite(((WaterSplashParticle$SplashFactoryAccessor) this).dripstone_fluid_lib$spriteProvider());
			int color = fluid.getParticleColor(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			float r = (color >> 16 & 255) / 255f;
			float g = (color >> 8 & 255) / 255f;
			float b = (color & 255) / 255f;
			particle.setColor(r, g, b);
			return particle;
		}
	}
}
