package ninjaphenix.noncorrelatedextras.module.polarizediron.items;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class MagnetisedArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private static final int[] PROTECTION_AMOUNTS = new int[]{1, 4, 5, 2};

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getIndex()] * 15;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return PROTECTION_AMOUNTS[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_CHAIN;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Registry.ITEM.get(new ResourceLocation("noncorrelatedextras", "polarized_iron_ingot")));
    }

    @Override
    public String getName() {
        return "polarized_iron";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
