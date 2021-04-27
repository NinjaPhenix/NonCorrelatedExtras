package ninjaphenix.noncorrelatedextras.module.polarizediron.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.UUID;

public class MagnetisedArmourItem extends ArmorItem {
    public static final Attribute MAGNET_RANGE = new RangedAttribute("noncorrelatedextras.attribute.max_magnet_range", 0, 0, Double.MAX_VALUE);
    private static final ArmorMaterial MATERIAL = new MagnetisedArmorMaterial();
    private final int magnetModifier;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public MagnetisedArmourItem(EquipmentSlot slot, Properties settings) {
        super(MATERIAL, slot, settings);
        //todo: magnetModifier = Config.INSTANCE.getAdditionalMagnetRange(slot);
        magnetModifier = 1;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder().putAll(super.getDefaultAttributeModifiers(slot));
        builder.put(MAGNET_RANGE, new AttributeModifier(UUID.randomUUID(), "Magnet modifier", this.magnetModifier, AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == this.slot ? defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }
}
