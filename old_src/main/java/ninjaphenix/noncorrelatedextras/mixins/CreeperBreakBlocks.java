package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreeperEntity.class)
public abstract class CreeperBreakBlocks
{
	@ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
	private Explosion.DestructionType modifiedArgument(Explosion.DestructionType type)
	{
		if (type == Explosion.DestructionType.DESTROY) { return Explosion.DestructionType.BREAK; }
		return type;
	}
}