package ninjaphenix.noncorrelatedextras.module.nbttooltips.mixins;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ninjaphenix.noncorrelatedextras.module.nbttooltips.NbtTooltips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public class ItemLinkingMixin {
    @Shadow
    protected Slot hoveredSlot;

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    public void chatItem(int key, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPlayNetworking.canSend(NbtTooltips.ITEM_CHAT)) {
            if (Minecraft.getInstance().options.keyChat.matches(key, scanCode) && Screen.hasShiftDown()) {
                if (hoveredSlot != null && hoveredSlot.hasItem()) {
                    ItemStack stack = hoveredSlot.getItem();
                    FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                    buffer.writeItem(stack);
                    ClientPlayNetworking.send(NbtTooltips.ITEM_CHAT, buffer);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
