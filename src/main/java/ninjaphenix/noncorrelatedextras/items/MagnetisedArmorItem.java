package ninjaphenix.noncorrelatedextras.items;

import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import ninjaphenix.noncorrelatedextras.config.Config;

import java.util.UUID;

public class MagnetisedArmorItem extends ArmorItem
{
    private static final EntityAttribute MAGNET_RANGE = new ClampedEntityAttribute(null, "generic.max_magnet_range", 0, 0, Double.MAX_VALUE);
    private static final ArmorMaterial MATERIAL = new MagnetisedArmorMaterial();
    private final int magnetModifier;

    public MagnetisedArmorItem(EquipmentSlot slot, Settings settings)
    {
        super(MATERIAL, slot, settings);
        magnetModifier = Config.INSTANCE.getAdditionalMagnetRange(slot);
    }

    @Override
    public Multimap<String, EntityAttributeModifier> getModifiers(EquipmentSlot slot)
    {
        Multimap<String, EntityAttributeModifier> multimap = super.getModifiers(slot);
        if (slot == this.slot)
        {
            multimap.put(MAGNET_RANGE.getId(),
                    new EntityAttributeModifier(UUID.randomUUID(), "Magnet modifier", this.magnetModifier, EntityAttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }
}
