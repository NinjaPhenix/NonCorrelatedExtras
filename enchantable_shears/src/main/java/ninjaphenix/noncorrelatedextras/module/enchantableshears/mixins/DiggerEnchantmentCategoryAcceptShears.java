package ninjaphenix.noncorrelatedextras.module.enchantableshears.mixins;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/item/enchantment/EnchantmentCategory$7")
public class DiggerEnchantmentCategoryAcceptShears {
    @Inject(method = "canEnchant(Lnet/minecraft/world/item/Item;)Z", at=@At("HEAD"), cancellable = true)
    private void noncorrelatedextras_canEnchant(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item instanceof ShearsItem) {
            cir.setReturnValue(true);
        }
    }
}