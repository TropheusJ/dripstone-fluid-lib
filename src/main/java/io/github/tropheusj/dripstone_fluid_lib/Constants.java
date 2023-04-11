package io.github.tropheusj.dripstone_fluid_lib;

import com.google.common.collect.ImmutableList;

import net.minecraft.util.Identifier;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Constants {
	public static final List<Identifier> DRIP_HANG = ImmutableList.of(new Identifier("minecraft", "drip_hang"));
	public static final List<Identifier> DRIP_FALL = ImmutableList.of(new Identifier("minecraft", "drip_fall"));
	public static final List<Identifier> SPLASH = ImmutableList.of(
			new Identifier("dripstone_fluid_lib", "splash_0"),
			new Identifier("dripstone_fluid_lib", "splash_1"),
			new Identifier("dripstone_fluid_lib", "splash_2"),
			new Identifier("dripstone_fluid_lib", "splash_3")
	);

	public static final Map<DripstoneInteractingFluid, ParticleTypeSet> FLUIDS_TO_PARTICLES = new HashMap<>();
	@Internal
	public static final Set<DripstoneInteractingFluid> TO_REGISTER = new HashSet<>();
}
