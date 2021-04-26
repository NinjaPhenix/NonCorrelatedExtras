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
import ninjaphenix.noncorrelatedextras.features.MagnetFeature;

import java.util.List;

public class MagnetItem extends Item
{
	private static final int MAX_RANGE = Config.INSTANCE.getMagnetMaxRange();
	private static final double SPEED = Config.INSTANCE.getMagnetSpeed();
	private static final EquipmentSlot[] EQUIPMENT_SLOTS = new EquipmentSlot[]
			{ EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };

	public MagnetItem(Settings settings) { super(settings); }

	public static void magnetTick(PlayerEntity player, ItemStack stack)
	{
		if (player.isSneaking()) { return; }
		ensureValidMagnetRange(player, stack);
		final int range = getMagnetRange(stack);
		final Vec3d finePos = player.getPos().add(0, 0.25, 0);
		final List<ItemEntity> entities = player.getEntityWorld().getEntities(EntityType.ITEM, new Box(finePos.subtract(range, range, range),
				finePos.add(range, range, range)), EntityPredicates.EXCEPT_SPECTATOR);
		if (getMagnetMode(stack)) { for (ItemEntity item : entities) { if (!item.cannotPickup()) { item.onPlayerCollision(player); } } }
		else { for (ItemEntity item : entities) { item.setVelocity(finePos.subtract(item.getPos()).multiply(SPEED)); } }
	}

	private static void ensureValidMagnetRange(PlayerEntity player, ItemStack stack)
	{
		final int range = getMagnetRange(stack);
		if (range > MAX_RANGE)
		{
			final int max = getMagnetMaxRange(player);
			if (range > max) { setMagnetRange(stack, max); }
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		if (slot < 9 && entity instanceof PlayerEntity) { magnetTick((PlayerEntity) entity, stack); }
	}

	public static int getMagnetMaxRange(PlayerEntity player)
	{
		int range = MAX_RANGE;
		if (player != null)
		{
			for (EquipmentSlot type : EQUIPMENT_SLOTS)
			{ range += player.getEquippedStack(type).getItem() instanceof MagnetisedArmourItem ? Config.INSTANCE.getAdditionalMagnetRange(type) : 0; }
		}
		return range;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack stack = user.getStackInHand(hand);
		if (!world.isClient) { if (user.isSneaking()) { MagnetFeature.openMagnetScreen(user, stack); } }
		return TypedActionResult.success(stack);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
	{
		final int magnet_range = getMagnetMaxRange(MinecraftClient.getInstance().player);
		Text rangeText = new TranslatableText("noncorrelatedextras.tooltip.magnet.range",
				new LiteralText(String.valueOf(getMagnetRange(stack))).formatted(Formatting.DARK_GRAY),
				new LiteralText(String.valueOf(magnet_range)).formatted(Formatting.DARK_GRAY)).formatted(Formatting.GRAY);
		if (magnet_range > MAX_RANGE)
		{ rangeText = new TranslatableText("noncorrelatedextras.tooltip.magnet.extra_range", rangeText, magnet_range - MAX_RANGE).formatted(Formatting.BLUE); }
		tooltip.add(rangeText);
	}

	public static int getMagnetRange(ItemStack stack)
	{
		CompoundTag tag = stack.getOrCreateTag();
		if (!tag.contains("range")) { tag.putInt("range", MAX_RANGE); }
		return tag.getInt("range");
	}

	public static void setMagnetRange(ItemStack stack, int range)
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("range", range);
	}

	public static void setMagnetMode(ItemStack stack, boolean mode)
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("mode", mode);
	}

	public static boolean getMagnetMode(ItemStack stack)
	{
		CompoundTag tag = stack.getOrCreateTag();
		if (!tag.contains("mode")) { tag.putBoolean("mode", false); }
		return tag.getBoolean("mode");
	}
}
