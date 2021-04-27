package ninjaphenix.noncorrelatedextras.module.polarizediron;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import ninjaphenix.noncorrelatedextras.module.polarizediron.items.MagnetisedArmourItem;

public class PolarizedIron implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_ingot"), new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        final Block polarizedIronBlock = Registry.register(Registry.BLOCK, new ResourceLocation("noncorrelatedextras", "polarized_iron_block"), new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        Registry.register(Registry.ITEM, new ResourceLocation("noncorrelatedextras", "polarized_iron_block"), new BlockItem(polarizedIronBlock, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

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
