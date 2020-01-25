package ninjaphenix.noncorrelatedextras.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import ninjaphenix.noncorrelatedextras.Main;
import ninjaphenix.noncorrelatedextras.features.MagnetFeature;

@Environment(EnvType.CLIENT)
public class MagnetScreen extends Screen
{
    private static final Identifier SCREEN_TEXTURE = Main.getId("textures/screen/magnet.png");
    private final int MAX_RANGE;
    private int x;
    private static final int WIDTH = 238;
    private static final int HEIGHT = 44;
    private int y;
    private int magnetRange;
    private boolean magnetMode;

    public MagnetScreen(Text title, int maxRange, int currentRange, boolean currentMode)
    {
        super(title);
        MAX_RANGE = maxRange;
        magnetRange = currentRange;
        magnetMode = currentMode;
    }

    @Override
    public void init(MinecraftClient client, int width, int height)
    {
        this.x = (width - WIDTH) / 2;
        this.y = (height - HEIGHT) / 2;
        super.init(client, width, height);
        addButton(new CustomSliderWidget(x + 7, y + 17, 200, 20, (magnetRange + .0D) / (MAX_RANGE + 1))
        {
            @Override
            protected void updateMessage() { setMessage(new TranslatableText("screen.noncorrelatedextras.magnet.range", magnetRange).asString()); }

            @Override
            protected void applyValue() { magnetRange = (int) (1 + Math.round(MAX_RANGE * value)); }
        });
        addButton(new MagnetCheckButtonWidget(x + 211, y + 17, 20, 20, magnetMode, this)
		{
			@Override
			void onValueChanged(boolean value) { MagnetScreen.this.magnetMode = value; }
		});
    }

    @Override
    public void onClose()
	{
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
		buffer.writeInt(magnetRange);
		buffer.writeBoolean(magnetMode);
		ClientSidePacketRegistry.INSTANCE.sendToServer(MagnetFeature.UPDATE_VALUES_PACKET_ID, buffer);
		super.onClose();
	}

    @Override
    public boolean isPauseScreen() { return false; }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == 256 || minecraft.options.keyInventory.matchesKey(keyCode, scanCode))
        {
            onClose();
            return true;
        }
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta)
    {
        renderBackground();

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        this.blit(x, y, 0, 0, WIDTH, HEIGHT);

        super.render(mouseX, mouseY, delta);
        this.font.draw(this.title.asFormattedString(), x + 8.0F, y + 6.0F, 4210752);
    }
}
