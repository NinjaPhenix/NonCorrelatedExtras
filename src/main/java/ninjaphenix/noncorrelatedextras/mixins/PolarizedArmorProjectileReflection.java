package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import ninjaphenix.noncorrelatedextras.config.Config;
import ninjaphenix.noncorrelatedextras.items.MagnetisedArmourItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = { ProjectileEntity.class, TridentEntity.class })
public class PolarizedArmorProjectileReflection
{
	private static final double chance = Config.INSTANCE.getProjectileReflectionChance();

	@Inject(method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V", at = @At("HEAD"), cancellable = true)
	private void onEntityHit(EntityHitResult hit, CallbackInfo ci)
	{
		if (hit.getEntity().world.getRandom().nextDouble() <= chance)
		{
			final Entity entity = hit.getEntity();
			if (entity instanceof PlayerEntity)
			{
				final PlayerEntity p = (PlayerEntity) entity;
				if (p.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof MagnetisedArmourItem)
				{
					if (p.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof MagnetisedArmourItem)
					{
						if (p.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof MagnetisedArmourItem)
						{
							if (p.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof MagnetisedArmourItem)
							{
								final ProjectileEntity self = (ProjectileEntity) (Object) this;
								self.setVelocity(self.getVelocity().multiply(-1));
								ci.cancel();
							}
						}
					}
				}
			}
		}
	}
}
