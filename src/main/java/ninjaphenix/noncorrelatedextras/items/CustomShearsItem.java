package ninjaphenix.noncorrelatedextras.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.BlockTags;

public class CustomShearsItem extends MiningToolItem
{
	public CustomShearsItem(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings)
	{
		super(attackDamage, attackSpeed, material, null, settings);
	}

	@Override
	public float getMiningSpeed(ItemStack stack, BlockState state)
	{
		Block block = state.getBlock();
		if (block == Blocks.COBWEB || BlockTags.LEAVES.contains(block)) { return 15.0f; }
		return isEffectiveOn(state) ? miningSpeed : 1.0f;
	}

	@Override
	public boolean isEffectiveOn(BlockState state)
	{
		Block block = state.getBlock();
		return BlockTags.WOOL.contains(block) || BlockTags.LEAVES.contains(block) || BlockTags.CARPETS.contains(block) ||
				block == Blocks.COBWEB || block == Blocks.REDSTONE_WIRE || block == Blocks.TRIPWIRE;
	}
}
