package ninjaphenix.noncorrelatedextras.mixins;

import dev.emi.trinkets.api.ITrinket;
import dev.emi.trinkets.api.Slots;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import ninjaphenix.noncorrelatedextras.items.MagnetItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagnetItem.class)
public class MagnetTrinketCompat implements ITrinket
{
	@Override
	public boolean canWearInSlot(String group, String slot)
	{
		return slot.equals(Slots.RING);
	}

	@Override
	public void tick(PlayerEntity player, ItemStack stack) { MagnetItem.magnetTick(player, stack); }

	@Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", cancellable = true)
	private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir)
	{
		if (!user.isSneaking())
		{
			cir.setReturnValue(ITrinket.equipTrinket(user, hand));
		}
	}
}
