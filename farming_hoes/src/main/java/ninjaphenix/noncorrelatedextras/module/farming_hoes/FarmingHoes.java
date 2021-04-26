package ninjaphenix.noncorrelatedextras.module.farming_hoes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FarmingHoes implements ClientModInitializer {
    // todo-norelease: rework for 1.16 hoe effective tool change.
    @Override
    public void onInitializeClient() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            final ItemStack handStack = player.getItemInHand(hand);
            if (handStack.getItem() instanceof HoeItem) {
                final BlockState state = world.getBlockState(pos);
                final Block block = state.getBlock();
                if (block instanceof CropBlock) {
                    final CropBlock cropBlock = (CropBlock) block;
                    if (state.getValue(cropBlock.getAgeProperty()) == cropBlock.getMaxAge()) {
                        return InteractionResult.PASS;
                    }
                } else if (block instanceof CactusBlock || block instanceof SugarCaneBlock) {
                    if (world.getBlockState(pos.below()).getBlock() == block) {
                        return InteractionResult.PASS;
                    }
                } else if (block instanceof CocoaBlock) {
                    if (state.getValue(CocoaBlock.AGE) == 2) {
                        return InteractionResult.PASS;
                    }
                } else if (block instanceof PumpkinBlock || block instanceof MelonBlock || block instanceof CarvedPumpkinBlock /* 1.12.2 support*/) {
                    return InteractionResult.PASS;
                }
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });
    }
}
