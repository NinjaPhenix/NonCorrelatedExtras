package ninjaphenix.noncorrelatedextras.config;

import blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;

import java.nio.file.Path;
import java.util.HashMap;

public class Config
{
	public static Config INSTANCE;

	@Comment("\nThe max range of the magnet.")
	private final int magnet_range = 6;
	@Comment("\nWhat fraction of the distance between the item and player the item moves per tick.")
	private final double magnet_speed = 0.1;
	@Comment("\nThe chance a projectile is reflected by polarized iron armor. (set bonus)")
	private final double polarized_iron_armor_reflection_chance = 0.15D;
	@Comment("\nEnabled features.")
	private final HashMap<String, Boolean> enabled_features = getDefaultEnabledFeatures();
	@Comment("\nMax magnet range increases for each polarized iron armor piece.")
	private final HashMap<EquipmentSlot, Integer> polarized_iron_armor_additional_magnet_range = getDefaultMagnetRangeAdditions();

	private HashMap<EquipmentSlot, Integer> getDefaultMagnetRangeAdditions()
	{
		HashMap<EquipmentSlot, Integer> rv = new HashMap<>();
		rv.put(EquipmentSlot.HEAD, 2);
		rv.put(EquipmentSlot.CHEST, 4);
		rv.put(EquipmentSlot.LEGS, 3);
		rv.put(EquipmentSlot.FEET, 1);
		return rv;
	}

	private HashMap<String, Boolean> getDefaultEnabledFeatures()
	{
		HashMap<String, Boolean> map = new HashMap<>();
		map.put("magnet", true);
		map.put("creepers_break_blocks", false);
		map.put("enchantable_shears", false);
		map.put("polarized_iron_armor", true);
		map.put("polarized_iron_armor_reflects_projectiles", true);
		map.put("farming_hoes", false);
		map.put("peaceful_only_recipes", false);
		return map;
	}

	public static void initialize()
	{
		Path configDirectory = FabricLoader.getInstance().getConfigDirectory().toPath();
		INSTANCE = ConfigManager.loadConfig(Config.class, configDirectory.resolve("NonCorrelatedExtras.json").toFile(), Config::new);
	}

	public Boolean isFeatureEnabled(String feature) { return feature == null || enabled_features.getOrDefault(feature, Boolean.FALSE); }

	public double getMagnetSpeed() { return magnet_speed; }

	public int getMagnetMaxRange() { return magnet_range; }

	public int getAdditionalMagnetRange(EquipmentSlot type) { return polarized_iron_armor_additional_magnet_range.getOrDefault(type, 0); }

	public double getProjectileReflectionChance() { return polarized_iron_armor_reflection_chance; }
}
