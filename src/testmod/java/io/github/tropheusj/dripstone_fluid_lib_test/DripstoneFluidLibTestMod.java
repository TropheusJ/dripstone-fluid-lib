package io.github.tropheusj.dripstone_fluid_lib_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MapColor;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DripstoneFluidLibTestMod implements ModInitializer {
	public static final String ID = "dripstone_fluid_lib_test";
	public static FlowableFluid STILL_FLUID;
	public static FlowableFluid FLOWING_FLUID;
	public static Block FLUID;
	public static Item BUCKET;

	@Override
	public void onInitialize() {
		STILL_FLUID = Registry.register(Registry.FLUID, id("fluid"), new Fluid.Still());
		FLOWING_FLUID = Registry.register(Registry.FLUID, id("flowing_fluid"), new Fluid.Flowing());
		FLUID = Registry.register(Registry.BLOCK, id("fluid_block"),
				new FluidBlock(STILL_FLUID, FabricBlockSettings.copy(Blocks.WATER).mapColor(MapColor.WHITE)){});
		BUCKET = Registry.register(Registry.ITEM, id("fluid_bucket"), new BucketItem(STILL_FLUID, new FabricItemSettings()));
	}

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
}
