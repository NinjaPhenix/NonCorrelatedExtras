package ninjaphenix.noncorrelatedextras.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class MagnetItem extends Item
{
    private static final int MAX_RANGE = 6;

    public MagnetItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if (slot < 9 && isMagnetActive(stack))
        {
            final int range = getMagnetRange(stack);
            final Vec3d finePos = entity.getPos();
            List<ItemEntity> entities = world.getEntities(EntityType.ITEM, new Box(finePos.subtract(range, range, range),
                    finePos.add(range, range,range)), EntityPredicates.EXCEPT_SPECTATOR);
            entities.forEach((item) ->
            {
                final Vec3d vel = finePos.subtract(item.getPos()).multiply(0.1);
                item.setVelocity(vel);
            });
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient)
        {
            if (user.isSneaking())
            {
                setMagnetActive(stack, !isMagnetActive(stack));
            }
            else
            {
                increaseMagnetRange(stack);
            }

        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        final Text active = new TranslatableText("noncorrelatedextras.tooltip.magnetitem.active").formatted(Formatting.GRAY);
        if (isMagnetActive(stack))
            active.append(new TranslatableText("noncorrelatedextras.tooltip.magnetitem.on").formatted(Formatting.GREEN));
        else
            active.append(new TranslatableText("noncorrelatedextras.tooltip.magnetitem.off").formatted(Formatting.RED));
        tooltip.add(active);
        tooltip.add(new TranslatableText("noncorrelatedextras.tooltip.magnetitem.range")
                .formatted(Formatting.GRAY).append(new LiteralText("" + getMagnetRange(stack)).formatted(Formatting.DARK_GRAY)));
    }

    private int getMagnetRange(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("range")) tag.putInt("range", MAX_RANGE);
        return tag.getInt("range");
    }

    private void increaseMagnetRange(ItemStack stack)
    {
        int magnetRange = getMagnetRange(stack) + 1;
        if (magnetRange > MAX_RANGE) magnetRange = 1;
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("range", magnetRange);
    }

    private boolean isMagnetActive(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("active")) tag.putBoolean("active", false);
        return tag.getBoolean("active");
    }

    private void setMagnetActive(ItemStack stack, boolean active)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean("active", active);
    }
}
