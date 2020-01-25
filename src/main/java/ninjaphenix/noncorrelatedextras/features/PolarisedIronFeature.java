package ninjaphenix.noncorrelatedextras.features;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.Main;
import ninjaphenix.noncorrelatedextras.core.BlockAdder;
import ninjaphenix.noncorrelatedextras.core.Feature;
import ninjaphenix.noncorrelatedextras.core.ItemAdder;

public class PolarisedIronFeature extends Feature implements BlockAdder, ItemAdder
{
	@Override
	public void registerBlocks()
	{
		Registry.register(Registry.BLOCK, Main.getId("polarized_iron_block"), new Block(Block.Settings.copy(Blocks.IRON_BLOCK)));
	}

	@Override
	public void registerItems()
	{
		Registry.register(Registry.ITEM, Main.getId("polarized_iron_ingot"), new Item(new Item.Settings().group(ItemGroup.MISC)));
		Identifier blockId = Main.getId("polarized_iron_block");
		Registry.register(Registry.ITEM, blockId, new BlockItem(Registry.BLOCK.get(blockId), new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
