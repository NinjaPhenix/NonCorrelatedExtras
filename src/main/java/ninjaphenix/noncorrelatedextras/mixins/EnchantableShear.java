package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.items.CustomShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Items.class)
public class EnchantableShear
{
    @Inject(method="register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", cancellable = true,
            at=@At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void register(Identifier id, Item item, CallbackInfoReturnable<Item> cir)
    {
        if(item instanceof ShearsItem)
        {
            cir.setReturnValue(Registry.register(Registry.ITEM, id, new CustomShearsItem(1.0f, 1.0f, ToolMaterials.IRON, new Item.Settings().maxDamage(238))));
        }
    }
}
