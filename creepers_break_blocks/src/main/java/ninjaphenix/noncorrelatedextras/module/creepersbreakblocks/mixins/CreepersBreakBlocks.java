package ninjaphenix.noncorrelatedextras.module.creepersbreakblocks.mixins;

import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Creeper.class)
public class CreepersBreakBlocks {
    // todo: should I replace this with a mixin that injects before the explode method?
    @ModifyArg(method = "explodeCreeper", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Explosion$BlockInteraction;)Lnet/minecraft/world/level/Explosion;"))
    private Explosion.BlockInteraction modifiedArgument(Explosion.BlockInteraction type) {
        if (type == Explosion.BlockInteraction.DESTROY) {
            return Explosion.BlockInteraction.BREAK;
        }
        return type;
    }
}
