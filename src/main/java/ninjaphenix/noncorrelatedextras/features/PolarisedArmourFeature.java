package ninjaphenix.noncorrelatedextras.features;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.Main;
import ninjaphenix.noncorrelatedextras.core.Feature;
import ninjaphenix.noncorrelatedextras.core.ItemAdder;
import ninjaphenix.noncorrelatedextras.items.MagnetisedArmourItem;

public class PolarisedArmourFeature extends Feature implements ItemAdder
{
    @Override
    public void registerItems()
    {
        final Item.Settings settings = new Item.Settings().group(ItemGroup.COMBAT);
        Registry.register(Registry.ITEM, Main.getId("polarized_iron_helmet"), new MagnetisedArmourItem(EquipmentSlot.HEAD, settings));
        Registry.register(Registry.ITEM, Main.getId("polarized_iron_chestplate"), new MagnetisedArmourItem(EquipmentSlot.CHEST, settings));
        Registry.register(Registry.ITEM, Main.getId("polarized_iron_leggings"), new MagnetisedArmourItem(EquipmentSlot.LEGS, settings));
        Registry.register(Registry.ITEM, Main.getId("polarized_iron_boots"), new MagnetisedArmourItem(EquipmentSlot.FEET, settings));
    }
}
