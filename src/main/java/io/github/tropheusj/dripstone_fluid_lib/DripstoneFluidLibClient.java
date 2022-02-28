package io.github.tropheusj.dripstone_fluid_lib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DripstoneFluidLibClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			for (int i = 0; i <= 3; i++) {
				registry.register(new Identifier("dripstone_fluid_lib", "particle/splash_" + i));
			}
		}));
	}
}
