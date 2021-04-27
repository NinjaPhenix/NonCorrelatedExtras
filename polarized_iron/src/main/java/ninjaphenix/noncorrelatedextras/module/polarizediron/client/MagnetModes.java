package ninjaphenix.noncorrelatedextras.module.polarizediron.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

@EnvironmentInterface(value = EnvType.CLIENT, itf = ButtonModeProvider.class)
public enum MagnetModes implements ButtonModeProvider
{
	PULL("screen/pull"),
	TELEPORT("screen/teleport");

	private TranslatableComponent translation;
	private Material texture;

	MagnetModes(String texturePath)
	{
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			this.texture = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("noncorrelatedextras", texturePath));
			this.translation = new TranslatableComponent("screen.noncorrelatedextras.magnet." + texturePath.substring(texturePath.lastIndexOf('/') + 1));
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public TextureAtlasSprite getSprite() { return texture.sprite(); }

	@Override
	@Environment(EnvType.CLIENT)
	public Component getText() { return translation; }
}
