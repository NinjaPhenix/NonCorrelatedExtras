package ninjaphenix.noncorrelatedextras.features;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import ninjaphenix.noncorrelatedextras.core.Feature;

public class FarmingHoesFeature extends Feature
{
	@Override
	public void initialiseClient()
	{
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
		{
			ItemStack handStack = player.getStackInHand(hand);
			if (handStack.getItem() instanceof HoeItem)
			{
				BlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				if (block instanceof CropBlock)
				{
					CropBlock cropBlock = (CropBlock) block;
					if (state.get(cropBlock.getAgeProperty()) == cropBlock.getMaxAge()) { return ActionResult.PASS; }
				}
				else if (block instanceof CactusBlock || block instanceof SugarCaneBlock)
				{
					if (world.getBlockState(pos.down()).getBlock() == block) { return ActionResult.PASS; }
				}
				else if (block instanceof CocoaBlock)
				{
					if (state.get(CocoaBlock.AGE) == 2) { return ActionResult.PASS; }
				}
				else if (block instanceof PumpkinBlock || block instanceof MelonBlock || block instanceof CarvedPumpkinBlock /* 1.12.2 support*/)
				{
					return ActionResult.PASS;
				}
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});
	}
}
