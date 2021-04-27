package ninjaphenix.noncorrelatedextras.module.polarizediron.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public interface ButtonModeProvider
{
	TextureAtlasSprite getSprite();

	Component getText();
}
