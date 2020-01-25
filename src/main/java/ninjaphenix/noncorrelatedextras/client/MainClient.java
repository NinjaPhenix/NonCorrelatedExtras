package ninjaphenix.noncorrelatedextras.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.text.Text;
import ninjaphenix.noncorrelatedextras.Main;
import ninjaphenix.noncorrelatedextras.client.screen.MagnetScreen;
import ninjaphenix.noncorrelatedextras.core.FeatureManager;
import ninjaphenix.noncorrelatedextras.features.config.MagnetFeatureConfig;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer
{
    public static final MainClient INSTANCE = new MainClient();

    @Override
    public void onInitializeClient()
    {
        FeatureManager.initialiseClient();
    }
}