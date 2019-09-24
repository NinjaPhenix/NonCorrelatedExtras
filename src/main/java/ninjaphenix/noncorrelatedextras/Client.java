package ninjaphenix.noncorrelatedextras;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.packet.ClientCommandC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class Client implements ClientModInitializer
{
    private final Logger LOGGER = Common.LOGGER;
    private boolean keyPressed = false;
    private FabricKeyBinding elytraBind;
    private long lastTime = 0;

    @Override
    public void onInitializeClient()
    {
        elytraBind = FabricKeyBinding.Builder.create(new Identifier(Common.MOD_ID, "toggle_elytra"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
                "key.category.misc").build();

        ClientTickCallback.EVENT.register(this::elytraBindTick);
    }

    private void elytraBindTick(MinecraftClient client)
    {
        if (client.world != null)
        {
            if (client.world.getTime() - lastTime > 5)
            {
                if (elytraBind.isPressed())
                {
                    if(!client.player.abilities.flying)
                    {
                        final ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
                        if (chest.getItem() instanceof ElytraItem && ElytraItem.isUsable(chest))
                        {
                            lastTime = client.world.getTime();
                            client.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(client.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                            client.player.addChatMessage(new LiteralText("Sent packet."), false);
                        }
                    }
                }
            }
        }
    }
}
