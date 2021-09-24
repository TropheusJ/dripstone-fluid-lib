package io.github.tropheusj.dripstone_fluid_lib;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.particle.ParticleEffect;

import net.minecraft.world.WorldEvents;

import org.jetbrains.annotations.Nullable;

public interface DripstoneInteractingFluid {
	float WATER_DRIP_CHANCE = 0.17578125F;
	float LAVA_DRIP_CHANCE = 0.05859375F;

	float getFluidDripChance();
	ParticleEffect getParticleEffect();
	boolean growsDripstone();

	/**
	 * The blockstate to set when dripstone drips this fluid into a {@link CauldronBlock}, filling it.
	 * returning null will prevent cauldron filling.
	 */
	@Nullable
	BlockState getCauldronBlockState();

	/**
	 * Use {@link WorldEvents#POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON} or
	 * {@link WorldEvents#POINTED_DRIPSTONE_DRIPS_LAVA_INTO_CAULDRON} if you don't want to make your own
	 */
	int getFluidDripWorldEvent();
}
