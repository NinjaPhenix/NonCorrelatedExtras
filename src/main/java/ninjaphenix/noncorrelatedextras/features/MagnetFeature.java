package ninjaphenix.noncorrelatedextras.features;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.Main;
import ninjaphenix.noncorrelatedextras.client.screen.MagnetScreen;
import ninjaphenix.noncorrelatedextras.core.Feature;
import ninjaphenix.noncorrelatedextras.core.ItemAdder;
import ninjaphenix.noncorrelatedextras.features.config.MagnetFeatureConfig;
import ninjaphenix.noncorrelatedextras.items.MagnetItem;

import java.util.HashMap;

public class MagnetFeature extends Feature implements ItemAdder
{
	public static final HashMap<PlayerEntity, ItemStack> USED_MAGNETS = new HashMap<>();
	public static final Identifier MAGNET_OPEN_SCREEN_PACKET_ID = Main.getId("open_magnet_screen");
	public static final Identifier UPDATE_VALUES_PACKET_ID = Main.getId("update_magnet_values");

	public static void openMagnetScreen(PlayerEntity player, ItemStack stack)
	{
		USED_MAGNETS.put(player, stack);
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
		buffer.writeText(stack.getName());
		buffer.writeInt(MagnetItem.getMagnetMaxRange(player));
		buffer.writeInt(MagnetItem.getMagnetRange(stack));
		buffer.writeBoolean(MagnetItem.getMagnetMode(stack));
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, MAGNET_OPEN_SCREEN_PACKET_ID, buffer);
	}

	@Override
	public void initialise()
	{
		ServerSidePacketRegistry.INSTANCE.register(UPDATE_VALUES_PACKET_ID, (context, buffer) ->
		{
			int range = buffer.readInt();
			boolean teleport = buffer.readBoolean();
			PlayerEntity player = context.getPlayer();
			context.getTaskQueue().execute(() ->
			{
				if (USED_MAGNETS.containsKey(player))
				{
					ItemStack stack = USED_MAGNETS.get(player);
					MagnetItem.setMagnetMode(stack, teleport);
					MagnetItem.setMagnetRange(stack, range);
				}
				else
				{
					player.sendMessage(new TranslatableText("text.noncorrelatedextras.magnet.fail_update_value"));
				}
				USED_MAGNETS.remove(player);
			});
		});

		if (MagnetFeatureConfig.isTrinketLoaded)
		{
			TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
			TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void initialiseClient()
	{
		ClientSidePacketRegistry.INSTANCE.register(MAGNET_OPEN_SCREEN_PACKET_ID, (context, buffer) ->
		{
			Text title = buffer.readText();
			int maxRange = buffer.readInt() - 1;
			int currentRange = buffer.readInt();
			boolean mode = buffer.readBoolean();
			context.getTaskQueue().execute(() ->
			{
				MinecraftClient.getInstance().openScreen(new MagnetScreen(title, maxRange, currentRange, mode));
			});
		});

		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((spriteAtlasTexture, registry) ->
		{
			registry.register(Main.getId("screen/pull"));
			registry.register(Main.getId("screen/teleport"));
		});
	}

	@Override
	public void registerItems()
	{
		Registry.register(Registry.ITEM, Main.getId("magnet"), new MagnetItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1)));
	}
}
