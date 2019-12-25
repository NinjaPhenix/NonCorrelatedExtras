package ninjaphenix.noncorrelatedextras.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import ninjaphenix.noncorrelatedextras.Main;

public class MagnetisedArmorMaterial implements ArmorMaterial
{
    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private static final int[] PROTECTION_AMOUNTS = new int[]{1, 4, 5, 2};

    @Override
    public int getDurability(EquipmentSlot slot) { return BASE_DURABILITY[slot.getEntitySlotId()] * 15; }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) { return PROTECTION_AMOUNTS[slot.getEntitySlotId()]; }

    @Override
    public int getEnchantability() { return 12; }

    @Override
    public SoundEvent getEquipSound() { return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN; }

    @Override
    public Ingredient getRepairIngredient() { return Ingredient.ofItems(Main.POLARIZED_IRON); }

    @Override
    public String getName() { return "polarized_iron"; }

    @Override
    public float getToughness() { return 0; }
}
