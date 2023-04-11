package io.github.tropheusj.dripstone_fluid_lib_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class DripstoneFluidLibTestMod implements ModInitializer {
	public static final String ID = "dripstone_fluid_lib_test";
	public static Map<Identifier, FlowableFluid> STILL = new HashMap<>(16);
	public static Map<Identifier, FlowableFluid> FLOWING = new HashMap<>(16);
	public static Map<Identifier, FluidBlock> FLUID = new HashMap<>(16);
	public static Map<Identifier, Item> BLUCKET = new HashMap<>(16);

	@Override
	public void onInitialize() {
		for (DyeColor color : DyeColor.values()) {
			String name = color.getName();
			Identifier still = id(name + "_still");
			Identifier flowing = id(name + "_flowing");
			Identifier fluid = id(name + "_fluid");
			Identifier bucket = id(name + "_bucket");

			TestFluid stillFluid = new TestFluid.Still(name, color.getFireworkColor());
			TestFluid flowingFluid = new TestFluid.Flowing(name, color.getFireworkColor());
			stillFluid.flowing = flowingFluid;
			flowingFluid.still = stillFluid;

			FluidBlock block = new FluidBlock(stillFluid, FabricBlockSettings.copyOf(Blocks.WATER)){};
			stillFluid.block = block;
			flowingFluid.block = block;

			BucketItem bucketItem = new BucketItem(stillFluid, new FabricItemSettings());
			stillFluid.bucket = bucketItem;
			flowingFluid.bucket = bucketItem;

			STILL.put(still, Registry.register(Registries.FLUID, still, stillFluid));
			FLOWING.put(flowing, Registry.register(Registries.FLUID, flowing, flowingFluid));
			FLUID.put(fluid, Registry.register(Registries.BLOCK, fluid, block));
			BLUCKET.put(bucket, Registry.register(Registries.ITEM, bucket, bucketItem));
		}
	}

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
}
