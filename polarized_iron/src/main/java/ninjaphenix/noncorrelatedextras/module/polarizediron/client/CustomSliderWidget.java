package ninjaphenix.noncorrelatedextras.module.polarizediron.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.TextComponent;

@Environment(EnvType.CLIENT)
public abstract class CustomSliderWidget extends AbstractSliderButton
{
	public CustomSliderWidget(int x, int y, int width, int height, double progress)
	{
		super(x, y, width, height, new TextComponent(""), progress);
		this.updateMessage();
	}
}
