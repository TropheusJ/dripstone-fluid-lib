package io.github.tropheusj.dripstone_fluid_lib;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public class DripstoneFluidParticle extends WaterSplashParticle {
	public DripstoneFluidParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
		super(clientWorld, d, e, f, g, h, i);
	}

	@Override
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.maxAge-- <= 0) {
			this.markDead();
		} else {
			this.velocityY -= this.gravityStrength;
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			this.velocityX *= 0.98F;
			this.velocityY *= 0.98F;
			this.velocityZ *= 0.98F;
			if (this.onGround) {
				if (Math.random() < 0.5) {
					this.markDead();
				}

				this.velocityX *= 0.7F;
				this.velocityZ *= 0.7F;
			}
		}
	}
}
