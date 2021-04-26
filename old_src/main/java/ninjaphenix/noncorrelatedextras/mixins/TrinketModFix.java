package ninjaphenix.noncorrelatedextras.mixins;

import dev.emi.trinkets.api.ITrinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjaphenix.noncorrelatedextras.items.MagnetItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class TrinketModFix
{
	@Shadow public ClientPlayerEntity player;

	@Inject(at = @At("HEAD"), method = "tick()V")
	private void tick(CallbackInfo ci)
	{
		if (player == null) { return; }
		final Inventory inv = TrinketsApi.getTrinketsInventory(player);
		for (int i = 0; i < inv.getInvSize(); i++)
		{
			final ItemStack stack = inv.getInvStack(i);
			final Item item = stack.getItem();
			if (item instanceof MagnetItem) { ((ITrinket) item).tick(player, stack); }
		}
	}
}
