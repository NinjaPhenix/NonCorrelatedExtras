package ninjaphenix.noncorrelatedextras.module.polarizediron;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import ninjaphenix.noncorrelatedextras.module.polarizediron.items.MagnetItem;
import ninjaphenix.noncorrelatedextras.module.polarizediron.items.MagnetisedArmourItem;

import java.util.HashMap;

public class PolarizedIron implements ModInitializer {
    public static final HashMap<Player, ItemStack> USED_MAGNETS = new HashMap<>();
    public static final ResourceLocation MAGNET_OPEN_SCREEN_PACKET_ID = new ResourceLocation("noncorrelatedextras", "open_magnet_screen");
    public static final ResourceLocation UPDATE_VALUES_PACKET_ID = new ResourceLocation("noncorrelatedextras", "update_magnet_values");

    public static void openMagnetScreen(ServerPlayer player, ItemStack stack) {
        USED_MAGNETS.put(player, stack);
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        buffer.writeComponent(stack.getHoverName());
        buffer.writeInt(MagnetItem.getMagnetMaxRange(player));
        buffer.writeInt(MagnetItem.getMagnetRange(stack));
        buffer.writeBoolean(MagnetItem.getMagnetMode(stack));
        ServerPlayNetworking.send(player, MAGNET_OPEN_SCREEN_PACKET_ID, buffer);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_ingot"), new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        final Block polarizedIronBlock = Registry.register(Registry.BLOCK, new ResourceLocation("noncorrelatedextras", "polarized_iron_block"), new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_block"), new BlockItem(polarizedIronBlock, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

        // Magnet
        Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "magnet"), new MagnetItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));

        ServerPlayConnectionEvents.INIT.register((handler, unused_server) -> {
            ServerPlayNetworking.registerReceiver(handler, UPDATE_VALUES_PACKET_ID, (server, player, unused_handler, buffer, responseSender) -> {
                int range = buffer.readInt();
                boolean teleport = buffer.readBoolean();
                server.submit(() -> {
                    if (USED_MAGNETS.containsKey(player)) {
                        ItemStack stack = USED_MAGNETS.get(player);
                        MagnetItem.setMagnetMode(stack, teleport);
                        MagnetItem.setMagnetRange(stack, range);
                    } else {
                        player.sendMessage(new TranslatableComponent("text.noncorrelatedextras.magnet.fail_update_value"), Util.NIL_UUID);
                    }
                    USED_MAGNETS.remove(player);
                });
            });
        });

        // Polarized Iron Armor
        final Item.Properties settings = new Item.Properties().tab(CreativeModeTab.TAB_COMBAT);
        final Item polarizedIronHelmet = Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_helmet"), new MagnetisedArmourItem(EquipmentSlot.HEAD, settings));
        final Item polarizedIronChestplate = Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_chestplate"), new MagnetisedArmourItem(EquipmentSlot.CHEST, settings));
        final Item polarizedIronLeggings = Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_leggings"), new MagnetisedArmourItem(EquipmentSlot.LEGS, settings));
        final Item polarizedIronBoots = Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_boots"), new MagnetisedArmourItem(EquipmentSlot.FEET, settings));

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            this.initializeClient(new Item[]{polarizedIronHelmet, polarizedIronChestplate, polarizedIronLeggings, polarizedIronBoots});
        }
    }

    private void initializeClient(Item[] armorItems) {
        ArmorRenderingRegistry.registerSimpleTexture(new ResourceLocation("noncorrelatedextras", "polarized_iron"), armorItems);
    }
}
