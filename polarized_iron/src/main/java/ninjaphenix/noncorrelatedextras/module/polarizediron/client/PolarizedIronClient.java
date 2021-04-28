package ninjaphenix.noncorrelatedextras.module.polarizediron.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static ninjaphenix.noncorrelatedextras.module.polarizediron.PolarizedIron.MAGNET_OPEN_SCREEN_PACKET_ID;

public class PolarizedIronClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.INIT.register((unused_handler, unused_client) -> {
            ClientPlayNetworking.registerReceiver(MAGNET_OPEN_SCREEN_PACKET_ID, (client, handler, buffer, responseSender) -> {
                Component title = buffer.readComponent();
                int maxRange = buffer.readInt() - 1;
                int currentRange = buffer.readInt();
                boolean mode = buffer.readBoolean();
                client.submit(() -> client.setScreen(new MagnetScreen(title, maxRange, currentRange, mode)));
            });
        });


        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register((spriteAtlasTexture, registry) -> {
            registry.register(new ResourceLocation("noncorrelatedextras", "screen/pull"));
            registry.register(new ResourceLocation("noncorrelatedextras", "screen/teleport"));
        });
    }
}
