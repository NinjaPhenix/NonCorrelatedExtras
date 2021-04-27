package ninjaphenix.noncorrelatedextras.module.farminghoes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FarmingHoes implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            final ItemStack handStack = player.getItemInHand(hand);
            if (handStack.getItem() instanceof HoeItem) {
                final BlockState state = level.getBlockState(pos);
                final Block block = state.getBlock();
                if (block instanceof CropBlock || block instanceof CocoaBlock) {
                    final BonemealableBlock growableBlock = (BonemealableBlock) block;
                    if (growableBlock.isValidBonemealTarget(level, pos, state, level.isClientSide())) {
                        return InteractionResult.FAIL;
                    }
                } else if (block instanceof CactusBlock || block instanceof SugarCaneBlock) {
                    if (level.getBlockState(pos.below()).getBlock() != block) {
                        return InteractionResult.FAIL;
                    }
                }
            }
            return InteractionResult.PASS; // Don't affect other items / let other handlers execute.
        });
    }
}
