package ninjaphenix.noncorrelatedextras;

import com.google.common.collect.Sets;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.blocks.BreakerBlock;
import ninjaphenix.noncorrelatedextras.blocks.entities.BreakerBlockEntity;
import ninjaphenix.noncorrelatedextras.config.Config;
import ninjaphenix.noncorrelatedextras.items.MagnetItem;
import ninjaphenix.noncorrelatedextras.items.MagnetisedArmorItem;

import java.nio.file.Files;
import java.util.Arrays;

public class Main implements ModInitializer
{
    public static final Main INSTANCE = new Main();
    public static final BlockEntityType<?> BREAKER_BE_TYPE;
    public static final Item POLARIZED_IRON;

    private Main() {}

    static
    {
        new String(new byte[]{1, 2, 3});
        BreakerBlock breaker = new BreakerBlock();
        BREAKER_BE_TYPE = new BlockEntityType<>(BreakerBlockEntity::new, Sets.newHashSet(breaker), null);
        //Registry.register(Registry.BLOCK_ENTITY, new Identifier("noncorrelatedextras", "block_breaker"), BREAKER_BE_TYPE);
        //Item breakerItem = new BlockItem(breaker, new Item.Settings().group(ItemGroup.REDSTONE));
        //Registry.register(Registry.BLOCK, new Identifier("noncorrelatedextras", "block_breaker"), breaker);
        //Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "block_breaker"), breakerItem);
        if (Config.INSTANCE.isFeatureEnabled("magnet"))
        {
            POLARIZED_IRON = new Item(new Item.Settings().group(ItemGroup.MISC));
            Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_ingot"), POLARIZED_IRON);
            Block polarizedIronBlock = new Block(Block.Settings.copy(Blocks.IRON_BLOCK));
            Registry.register(Registry.BLOCK, new Identifier("noncorrelatedextras", "polarized_iron_block"), polarizedIronBlock);
            Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_block"),
                    new BlockItem(polarizedIronBlock, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
            MagnetItem magnetItem = new MagnetItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
            Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "magnet"), magnetItem);
            if (FabricLoader.getInstance().isModLoaded("trinkets"))
            {
                TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
                TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
            }
            if (Config.INSTANCE.isFeatureEnabled("polarized_iron_armor"))
            {
                final Item.Settings settings = new Item.Settings().group(ItemGroup.COMBAT);
                MagnetisedArmorItem helmet = new MagnetisedArmorItem(EquipmentSlot.HEAD, settings);
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_helmet"), helmet);

                MagnetisedArmorItem chestplate = new MagnetisedArmorItem(EquipmentSlot.CHEST, settings);
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_chestplate"), chestplate);

                MagnetisedArmorItem leggings = new MagnetisedArmorItem(EquipmentSlot.LEGS, settings);
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_leggings"), leggings);

                MagnetisedArmorItem boots = new MagnetisedArmorItem(EquipmentSlot.FEET, settings);
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_boots"), boots);
            }
        }
        else
        {
            POLARIZED_IRON = null;
        }
    }

    @Override
    public void onInitialize() { }
}
