package ninjaphenix.noncorrelatedextras.module.polarizediron.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.phys.EntityHitResult;
import ninjaphenix.noncorrelatedextras.module.polarizediron.items.MagnetisedArmourItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = { Projectile.class, ThrownTrident.class })
public class PolarizedArmorProjectileReflection
{
	private static final double chance = 1; // todo: Config.INSTANCE.getProjectileReflectionChance()

	@Inject(method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V", at = @At("HEAD"), cancellable = true)
	private void onEntityHit(EntityHitResult hit, CallbackInfo ci)
	{
		if (hit.getEntity().getCommandSenderWorld().getRandom().nextDouble() <= chance)
		{
			final Entity entity = hit.getEntity();
			if (entity instanceof Player)
			{
				final Player p = (Player) entity;
				if (p.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof MagnetisedArmourItem)
				{
					if (p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof MagnetisedArmourItem)
					{
						if (p.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof MagnetisedArmourItem)
						{
							if (p.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof MagnetisedArmourItem)
							{
								final Projectile self = (Projectile) (Object) this;
								self.setDeltaMovement(self.getDeltaMovement().scale(-1));
								ci.cancel();
							}
						}
					}
				}
			}
		}
	}
}
