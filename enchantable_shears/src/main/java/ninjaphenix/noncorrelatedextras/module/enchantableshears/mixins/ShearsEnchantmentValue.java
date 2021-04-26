package ninjaphenix.noncorrelatedextras.module.enchantableshears.mixins;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.Tiers;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShearsItem.class)
public abstract class ShearsEnchantmentValue extends Item {
    private ShearsEnchantmentValue(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnchantmentValue() {
        return Tiers.IRON.getEnchantmentValue(); // todo: should they be iron tier?
    }
}
