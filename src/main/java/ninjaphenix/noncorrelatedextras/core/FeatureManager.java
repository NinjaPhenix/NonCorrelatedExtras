package ninjaphenix.noncorrelatedextras.core;

import ninjaphenix.noncorrelatedextras.config.Config;
import ninjaphenix.noncorrelatedextras.features.config.*;

import java.util.HashMap;
import java.util.HashSet;

public class FeatureManager
{
	private static final HashSet<FeatureConfig<? extends Feature>> configs = new HashSet<>();
	private static final HashSet<Feature> features = new HashSet<>();

	private FeatureManager() {}

	static
	{
		Config.initialize();
		configs.add(new MagnetFeatureConfig(Config.INSTANCE.isFeatureEnabled("magnet")));
		configs.add(new PolarisedIronFeatureConfig(Config.INSTANCE.isFeatureEnabled("magnet") || Config.INSTANCE.isFeatureEnabled("polarized_iron_armor")));
		configs.add(new PolarisedArmourFeatureConfig(Config.INSTANCE.isFeatureEnabled("polarized_iron_armor")));
		configs.add(new FarmingHoesFeatureConfig(Config.INSTANCE.isFeatureEnabled("farming_hoes")));
		configs.add(new EnchantableShearsFeatureConfig(Config.INSTANCE.isFeatureEnabled("enchantable_shears")));
		configs.add(new CreeperBreakBlocksFeatureConfig(Config.INSTANCE.isFeatureEnabled("creepers_break_blocks")));
	}

	public static void initialise()
	{
		configs.forEach(config ->
		{
			if (config.isEnabled()) { features.add(config.getFeature()); }
		});

		features.forEach(feature ->
		{
			feature.initialise();
			if (feature instanceof BlockAdder) { ((BlockAdder) feature).registerBlocks(); }
			if (feature instanceof ItemAdder) { ((ItemAdder) feature).registerItems(); }
		});
	}

	public static void initialiseClient() { features.forEach(Feature::initialiseClient); }

	public static HashMap<String, Boolean> getMixinMap()
	{
		HashMap<String, Boolean> map = new HashMap<>();
		configs.forEach(featureConfig -> featureConfig.getMixins().forEach((str, bool) ->
		{
			if (bool) { map.putIfAbsent(str, Boolean.TRUE); }
		}));
		return map;
	}
}
