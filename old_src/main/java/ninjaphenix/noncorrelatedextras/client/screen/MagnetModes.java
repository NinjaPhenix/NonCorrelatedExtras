package ninjaphenix.noncorrelatedextras.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import ninjaphenix.noncorrelatedextras.Main;

@EnvironmentInterface(value = EnvType.CLIENT, itf = ButtonModeProvider.class)
public enum MagnetModes implements ButtonModeProvider
{
	PULL("screen/pull"),
	TELEPORT("screen/teleport");

	private TranslatableText translation;
	private SpriteIdentifier texture;

	MagnetModes(String texturePath)
	{
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			this.texture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, Main.getId(texturePath));
			this.translation = new TranslatableText("screen.noncorrelatedextras.magnet." + texturePath.substring(texturePath.lastIndexOf('/') + 1));
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public Sprite getSprite() { return texture.getSprite(); }

	@Override
	@Environment(EnvType.CLIENT)
	public Text getText() { return translation; }
}
