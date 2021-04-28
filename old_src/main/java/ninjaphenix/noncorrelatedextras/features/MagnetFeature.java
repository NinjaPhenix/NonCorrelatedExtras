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
	@Override
	public void initialise()
	{
		if (MagnetFeatureConfig.isTrinketLoaded) {
			TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
			TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
		}
	}

	@Environment(EnvType.CLIENT)
	private static class Client
	{
		static void init()
		{

		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void initialiseClient()
	{
		Client.init();
	}
}
