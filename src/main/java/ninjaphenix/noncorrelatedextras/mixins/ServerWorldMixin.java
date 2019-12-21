package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin
{

    @Shadow @Final private List<ServerPlayerEntity> players;

    @Shadow private boolean allPlayersSleeping;

    @Shadow
    protected abstract void resetWeather();

    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At("HEAD"))
    private void tick(BooleanSupplier booleanSupplier, CallbackInfo ci)
    {
        ServerWorld self = (ServerWorld) (Object) this;
        if (players.stream().filter(PlayerEntity::isSleepingLongEnough).count() >= players.size() / 3.0D)
        {
            allPlayersSleeping = false;
            if (self.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                long l = self.getTimeOfDay() + 24000L;
                self.setTimeOfDay(l - l % 24000L);
            }
            players.stream().filter(LivingEntity::isSleeping).forEach(PlayerEntity::wakeUp);
            if (self.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
                resetWeather();
            }
        }
    }

}
