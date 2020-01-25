package ninjaphenix.noncorrelatedextras.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.texture.SpriteAtlasTexture;

public abstract class MagnetCheckButtonWidget extends AbstractPressableButtonWidget
{
	private final Screen screen;
	private boolean selected;

	public MagnetCheckButtonWidget(int x, int y, int width, int height, boolean selected, Screen parent)
	{
		super(x, y, width, height, "");
		this.selected = selected;
		this.screen = parent;
	}

	@Override
	public void onPress()
	{
		selected = !selected;
		onValueChanged(selected);
	}

	@Override
	public void renderButton(int mouseX, int mouseY, float delta)
	{
		super.renderButton(mouseX, mouseY, delta);
		MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
		ButtonModeProvider mode = selected ? MagnetModes.TELEPORT : MagnetModes.PULL;
		blit(x + 2, y + 2, getBlitOffset() + 200, 16, 16, mode.getSprite());
		if (isMouseOver(mouseX, mouseY)) { screen.renderTooltip(mode.getText().asFormattedString(), mouseX, mouseY); }
	}

	abstract void onValueChanged(boolean value);
}
