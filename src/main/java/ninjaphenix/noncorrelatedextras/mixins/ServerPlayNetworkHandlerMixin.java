package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements ServerPlayPacketListener
{

    @Shadow private Vec3d requestedTeleportPos;

    @Shadow public ServerPlayerEntity player;

    /**
     * @author NinjaPhenix
     */
    @Overwrite
    public void onClientCommand(ClientCommandC2SPacket packet)
    {
        NetworkThreadUtils.forceMainThread(packet, this, player.getServerWorld());
        player.updateLastActionTime();
        JumpingMount jumpingMount_1;
        switch (packet.getMode())
        {
            case START_SNEAKING:
                player.setSneaking(true);
                break;
            case STOP_SNEAKING:
                player.setSneaking(false);
                break;
            case START_SPRINTING:
                player.setSprinting(true);
                break;
            case STOP_SPRINTING:
                player.setSprinting(false);
                break;
            case STOP_SLEEPING:
                if (player.isSleeping())
                {
                    player.wakeUp(false, true, true);
                    requestedTeleportPos = new Vec3d(player.x, player.y, player.z);
                }
                break;
            case START_RIDING_JUMP:
                if (player.getVehicle() instanceof JumpingMount)
                {
                    jumpingMount_1 = (JumpingMount) player.getVehicle();
                    int int_1 = packet.getMountJumpHeight();
                    if (jumpingMount_1.canJump() && int_1 > 0)
                    {
                        jumpingMount_1.startJumping(int_1);
                    }
                }
                break;
            case STOP_RIDING_JUMP:
                if (player.getVehicle() instanceof JumpingMount)
                {
                    jumpingMount_1 = (JumpingMount) player.getVehicle();
                    jumpingMount_1.stopJumping();
                }
                break;
            case OPEN_INVENTORY:
                if (player.getVehicle() instanceof HorseBaseEntity)
                {
                    ((HorseBaseEntity) player.getVehicle()).openInventory(player);
                }
                break;
            case START_FALL_FLYING:
                ItemStack itemStack_1 = player.getEquippedStack(EquipmentSlot.CHEST);
                if (itemStack_1.getItem() == Items.ELYTRA && ElytraItem.isUsable(itemStack_1))
                {
                    if(player.isFallFlying())
                    {
                        player.method_14229();
                    }
                    else
                    {
                        player.method_14243();
                    }
                }
                else
                {
                    player.method_14229();
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid client command!");
        }

    }
}
