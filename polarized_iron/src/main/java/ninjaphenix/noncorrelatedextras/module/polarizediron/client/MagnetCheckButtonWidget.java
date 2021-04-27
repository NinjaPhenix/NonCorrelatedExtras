package ninjaphenix.noncorrelatedextras.module.polarizediron.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.TextComponent;

public abstract class MagnetCheckButtonWidget extends AbstractButton
{
	private final Screen screen;
	private boolean selected;

	public MagnetCheckButtonWidget(int x, int y, int width, int height, boolean selected, Screen parent)
	{
		super(x, y, width, height, new TextComponent(""));
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
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float delta) {
        super.renderButton(stack, mouseX, mouseY, delta);
        Minecraft.getInstance().getTextureManager().bind(TextureAtlas.LOCATION_BLOCKS);
        ButtonModeProvider mode = selected ? MagnetModes.TELEPORT : MagnetModes.PULL;
        blit(stack, x + 2, y + 2, getBlitOffset() + 200, 16, 16, mode.getSprite());
        if (isMouseOver(mouseX, mouseY)) { screen.renderTooltip(stack, mode.getText(), mouseX, mouseY); }
    }

    abstract void onValueChanged(boolean value);
}
