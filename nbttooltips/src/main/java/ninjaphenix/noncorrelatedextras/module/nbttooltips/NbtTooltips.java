package ninjaphenix.noncorrelatedextras.module.nbttooltips;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;

//todo: needs code clean up
public class NbtTooltips implements ModInitializer {
    public static final String MOD_ID = "noncorrelatedextras";
    public static final ResourceLocation ITEM_CHAT = new ResourceLocation(MOD_ID, "item_chat");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(NbtCommand::register);
        ServerPlayConnectionEvents.INIT.register((handler, server) -> {
            ServerPlayNetworking.registerReceiver(handler, ITEM_CHAT, NbtTooltips::itemChat);
        });
    }

    private static void itemChat(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener,
                                 FriendlyByteBuf buffer, PacketSender sender) {
        final ItemStack stack = buffer.readItem();
        final MutableComponent message = new TextComponent("<").append(player.getDisplayName()).append("> ");
        if (stack.isStackable()) {
            message.append(stack.getCount() + "x ");
        }
        message.append(stack.getDisplayName());
        player.getServer().getPlayerList().broadcastMessage(message, ChatType.CHAT, Util.NIL_UUID);
    }
}
