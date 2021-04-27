package ninjaphenix.noncorrelatedextras.module.polarizediron.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class MagnetScreen extends Screen
{
	private static final ResourceLocation SCREEN_TEXTURE = new ResourceLocation("noncorrelatedextras", "textures/screen/magnet.png");
	private final int MAX_RANGE;
	private int x;
	private static final int WIDTH = 238;
	private static final int HEIGHT = 44;
	private int y;
	private int magnetRange;
	private boolean magnetMode;

	public MagnetScreen(Component title, int maxRange, int currentRange, boolean currentMode)
	{
		super(title);
		MAX_RANGE = maxRange;
		magnetRange = currentRange;
		magnetMode = currentMode;
	}

	@Override
	public void init(Minecraft client, int width, int height)
	{
		this.x = (width - WIDTH) / 2;
		this.y = (height - HEIGHT) / 2;
		super.init(client, width, height);
		addButton(new CustomSliderWidget(x + 7, y + 17, 200, 20, (magnetRange + .0D) / (MAX_RANGE + 1))
		{
			@Override
			protected void updateMessage() { setMessage(new TranslatableComponent("screen.noncorrelatedextras.magnet.range", magnetRange)); }

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
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
		buffer.writeInt(magnetRange);
		buffer.writeBoolean(magnetMode);
		// todo: ClientSidePacketRegistry.INSTANCE.sendToServer(MagnetFeature.UPDATE_VALUES_PACKET_ID, buffer);
		super.onClose();
	}

	@Override
	public boolean isPauseScreen() { return false; }

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers)
	{
		if (keyCode == 256 || minecraft.options.keyInventory.matches(keyCode, scanCode))
		{
			onClose();
			return true;
		}
		return false;
	}

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
	    this.renderBackground(stack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bind(SCREEN_TEXTURE);
        this.blit(stack, x, y, 0, 0, WIDTH, HEIGHT);
        super.render(stack, mouseX, mouseY, delta);
        font.draw(stack, title, x + 8.0F, y + 6.0F, 4210752);
    }
}
