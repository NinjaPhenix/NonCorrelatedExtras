package ninjaphenix.noncorrelatedextras;

import com.google.common.collect.Sets;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.blocks.BreakerBlock;
import ninjaphenix.noncorrelatedextras.blocks.entities.BreakerBlockEntity;
import ninjaphenix.noncorrelatedextras.config.Config;
import ninjaphenix.noncorrelatedextras.items.MagnetItem;
import ninjaphenix.noncorrelatedextras.items.MagnetisedArmorItem;

public class Main implements ModInitializer
{
    public static final Main INSTANCE = new Main();
    public static final BlockEntityType<?> BREAKER_BE_TYPE;
    public static final Item POLARIZED_IRON;

    private Main() {}

    static
    {

        BreakerBlock breaker = new BreakerBlock();
        BREAKER_BE_TYPE = new BlockEntityType<>(BreakerBlockEntity::new, Sets.newHashSet(breaker), null);
        //Registry.register(Registry.BLOCK_ENTITY, new Identifier("noncorrelatedextras", "block_breaker"), BREAKER_BE_TYPE);
        //Item breakerItem = new BlockItem(breaker, new Item.Settings().group(ItemGroup.REDSTONE));
        //Registry.register(Registry.BLOCK, new Identifier("noncorrelatedextras", "block_breaker"), breaker);
        //Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "block_breaker"), breakerItem);
        if(Config.INSTANCE.isFeatureEnabled("magnet"))
        {
            POLARIZED_IRON = new Item(new Item.Settings());
            Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron"), POLARIZED_IRON);
            MagnetItem magnetItem = new MagnetItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
            Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "magnet"), magnetItem);
            if(FabricLoader.getInstance().isModLoaded("trinkets"))
            {
                TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
                TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
            }
            if(Config.INSTANCE.isFeatureEnabled("polarized_iron_armor"))
            {
                MagnetisedArmorItem helmet = new MagnetisedArmorItem(EquipmentSlot.HEAD, new Item.Settings());
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_helmet"), helmet);

                MagnetisedArmorItem chestplate = new MagnetisedArmorItem(EquipmentSlot.CHEST, new Item.Settings());
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_chestplate"), chestplate);

                MagnetisedArmorItem leggings = new MagnetisedArmorItem(EquipmentSlot.LEGS, new Item.Settings());
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_leggings"), leggings);

                MagnetisedArmorItem boots = new MagnetisedArmorItem(EquipmentSlot.FEET, new Item.Settings());
                Registry.register(Registry.ITEM, new Identifier("noncorrelatedextras", "polarized_iron_boots"), boots);
            }
        }
        else
        {
            POLARIZED_IRON = null;
        }
    }

    @Override
    public void onInitialize()
    {
    }
}
