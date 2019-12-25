package ninjaphenix.noncorrelatedextras.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ninjaphenix.noncorrelatedextras.config.Config;

import java.util.List;

public class MagnetItem extends Item
{
    private static final int MAX_RANGE = Config.INSTANCE.getMagnetMaxRange();
    private static final double SPEED = Config.INSTANCE.getMagnetSpeed();

    public MagnetItem(Settings settings) { super(settings); }

    public static void magnetTick(PlayerEntity player, ItemStack stack)
    {
        if(player.isSneaking()) return;
        final World world = player.getEntityWorld();

        final int range = getMagnetRange(stack);
        final Vec3d finePos = player.getPos().add(0, 0.25, 0);
        List<ItemEntity> entities = world.getEntities(EntityType.ITEM, new Box(finePos.subtract(range, range, range),
                finePos.add(range, range, range)), EntityPredicates.EXCEPT_SPECTATOR);
        entities.forEach((item) ->
        {
            final Vec3d vel = finePos.subtract(item.getPos()).multiply(SPEED);
            item.setVelocity(vel);
        });
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if (slot < 9 && entity instanceof PlayerEntity)
        {
            magnetTick((PlayerEntity) entity, stack);
        }
    }

    private int getMagnetMaxRange(PlayerEntity player)
    {
        int range = MAX_RANGE;
        if(player != null)
            for (EquipmentSlot type : new EquipmentSlot[]{ EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET })
                range += player.getEquippedStack(type).getItem() instanceof MagnetisedArmorItem ? Config.INSTANCE.getAdditionalMagnetRange(type) : 0;
        return range;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient)
        {
            if (user.isSneaking())
            {
                increaseMagnetRange(stack, user);
                // todo open magnet gui.
            }

        }
        return TypedActionResult.success(stack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
    {
        PlayerEntity player = MinecraftClient.getInstance().player;
        final int magnet_range = getMagnetMaxRange(player);
        Text rangeText = new TranslatableText("noncorrelatedextras.tooltip.magnet.range",
                new LiteralText(String.valueOf(getMagnetRange(stack))).formatted(Formatting.DARK_GRAY),
                new LiteralText(String.valueOf(magnet_range)).formatted(Formatting.DARK_GRAY)).formatted(Formatting.GRAY);
        if(magnet_range > MAX_RANGE)
            rangeText = new TranslatableText("noncorrelatedextras.tooltip.magnet.extra_range", rangeText, magnet_range - MAX_RANGE).formatted(Formatting.BLUE);
        tooltip.add(rangeText);
    }

    private static int getMagnetRange(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("range")) tag.putInt("range", MAX_RANGE);
        return tag.getInt("range");
    }

    private void increaseMagnetRange(ItemStack stack, PlayerEntity user)
    {
        int magnetRange = getMagnetRange(stack) + 1;
        if (magnetRange > getMagnetMaxRange(user)) magnetRange = 1;
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("range", magnetRange);
    }
}
