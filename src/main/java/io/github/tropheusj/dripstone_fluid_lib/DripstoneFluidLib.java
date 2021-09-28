package io.github.tropheusj.dripstone_fluid_lib;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.api.ModInitializer;
import net.minecraft.particle.DefaultParticleType;

public class DripstoneFluidLib implements ModInitializer {
														    // hang,				falling,			 splash
	public static final Map<DripstoneInteractingFluid, Triplet<DefaultParticleType, DefaultParticleType, DefaultParticleType>> FLUIDS_TO_PARTICLES = new HashMap<>();

	@Override
	public void onInitialize() {
	}
}
