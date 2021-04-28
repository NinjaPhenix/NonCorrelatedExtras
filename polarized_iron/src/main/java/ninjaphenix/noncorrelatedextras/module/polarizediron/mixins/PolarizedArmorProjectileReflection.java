package ninjaphenix.noncorrelatedextras.module.polarizediron.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import ninjaphenix.noncorrelatedextras.module.polarizediron.items.MagnetisedArmourItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {
        Projectile.class,
        DragonFireball.class,
        LargeFireball.class,
        ShulkerBullet.class,
        SmallFireball.class,
        Snowball.class,
        ThrownEgg.class,
        ThrownEnderpearl.class,
        ThrownExperienceBottle.class,
        ThrownPotion.class,
        WitherSkull.class })
public class PolarizedArmorProjectileReflection {
    private static final double chance = 1; // todo: Config.INSTANCE.getProjectileReflectionChance()

    @Inject(method = "onHit(Lnet/minecraft/world/phys/HitResult;)V", at = @At("HEAD"), cancellable = true)
    private void onEntityHit(HitResult hit, CallbackInfo ci) {
        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult hitResult = (EntityHitResult) hit;
            if (hitResult.getEntity().getCommandSenderWorld().getRandom().nextDouble() <= chance) {
                final Entity entity = hitResult.getEntity();
                if (entity instanceof Player) {
                    final Player p = (Player) entity;
                    if (p.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof MagnetisedArmourItem) {
                        if (p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof MagnetisedArmourItem) {
                            if (p.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof MagnetisedArmourItem) {
                                if (p.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof MagnetisedArmourItem) {
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
}
