package ninjaphenix.noncorrelatedextras.module.polarizediron.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import ninjaphenix.noncorrelatedextras.module.polarizediron.PolarizedIron;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagnetItem extends Item {
    private static final int MAX_RANGE = 4; // todo: 4 = Config.INSTANCE.getMagnetMaxRange()
    private static final double SPEED = 3; // todo: 3 = Config.INSTANCE.getMagnetSpeed()
    private static final EquipmentSlot[] EQUIPMENT_SLOTS = new EquipmentSlot[]
            {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public MagnetItem(Properties properties) {
        super(properties);
    }

    private static void magnetTick(ItemStack stack, Level level, Player player) {
        if (player.isCrouching()) {
            return;
        }
        ensureValidMagnetRange(player, stack);
        final int range = getMagnetRange(stack);
        final Vec3 finePos = player.position().add(0, 0.25, 0);
        final List<ItemEntity> entities = level.getEntities(
                EntityType.ITEM,
                new AABB(finePos.subtract(range, range, range), finePos.add(range, range, range)),
                EntitySelector.NO_SPECTATORS);
        if (getMagnetMode(stack)) {
            for (ItemEntity item : entities) {
                if (!item.hasPickUpDelay()) {
                    item.playerTouch(player);
                }
            }
        } else {
            for (ItemEntity item : entities) {
                item.setDeltaMovement(finePos.subtract(item.position()).scale(SPEED));
            }
        }
    }

    private static void ensureValidMagnetRange(Player player, ItemStack stack) {
        final int range = MagnetItem.getMagnetRange(stack);
        if (range > MAX_RANGE) {
            final int max = MagnetItem.getMagnetMaxRange(player);
            if (range > max) {
                MagnetItem.setMagnetRange(stack, max);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (slot < 9 && entity instanceof Player) {
            MagnetItem.magnetTick(stack, level, (Player) entity);
        }
    }

    public static int getMagnetMaxRange(Player player) {
        int range = MAX_RANGE;
        if (player != null) {
            for (EquipmentSlot type : EQUIPMENT_SLOTS) {
                range += player.getItemBySlot(type).getItem() instanceof MagnetisedArmourItem ? 1 : 0;
            } // todo: 1 = Config.INSTANCE.getAdditionalMagnetRange(type)
        }
        return range;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isCrouching()) {
            if (!level.isClientSide()) {
                PolarizedIron.openMagnetScreen((ServerPlayer) player, stack);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        final int magnet_range = getMagnetMaxRange(Minecraft.getInstance().player);
        Component rangeText = new TranslatableComponent("noncorrelatedextras.tooltip.magnet.range",
                                                        new TextComponent(String.valueOf(getMagnetRange(stack))).withStyle(ChatFormatting.DARK_GRAY),
                                                        new TextComponent(String.valueOf(magnet_range)).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY);
        if (magnet_range > MAX_RANGE) {
            rangeText = new TranslatableComponent("noncorrelatedextras.tooltip.magnet.extra_range", rangeText, magnet_range - MAX_RANGE).withStyle(ChatFormatting.BLUE);
        }
        tooltip.add(rangeText);
    }

    public static int getMagnetRange(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("range")) {
            tag.putInt("range", MAX_RANGE);
        }
        return tag.getInt("range");
    }

    public static void setMagnetRange(ItemStack stack, int range) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("range", range);
    }

    public static void setMagnetMode(ItemStack stack, boolean mode) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean("mode", mode);
    }

    public static boolean getMagnetMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("mode")) {
            tag.putBoolean("mode", false);
        }
        return tag.getBoolean("mode");
    }
}
