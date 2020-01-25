package ninjaphenix.noncorrelatedextras.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public interface ButtonModeProvider
{
    Sprite getSprite();

    Text getText();
}
