package ninjaphenix.noncorrelatedextras.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.SliderWidget;

@Environment(EnvType.CLIENT)
public abstract class CustomSliderWidget extends SliderWidget
{
	public CustomSliderWidget(int x, int y, int width, int height, double progress)
	{
		super(x, y, width, height, progress);
		this.updateMessage();
	}
}
