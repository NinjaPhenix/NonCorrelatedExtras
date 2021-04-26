package ninjaphenix.noncorrelatedextras.features.config;

import ninjaphenix.noncorrelatedextras.config.Config;
import ninjaphenix.noncorrelatedextras.core.FeatureConfig;
import ninjaphenix.noncorrelatedextras.features.PolarisedArmourFeature;

import java.util.HashMap;

public class PolarisedArmourFeatureConfig extends FeatureConfig<PolarisedArmourFeature>
{
	public PolarisedArmourFeatureConfig(Boolean enabled) { super(enabled); }

	@Override
	public HashMap<String, Boolean> getMixins()
	{
		HashMap<String, Boolean> map = super.getMixins();
		map.put("PolarizedArmorProjectileReflection", isEnabled() && Config.INSTANCE.isFeatureEnabled("polarized_iron_armor_reflects_projectiles"));
		map.put("PolarizedArmorTextureFix", isEnabled());
		return map;
	}

	@Override
	public PolarisedArmourFeature getFeature() { return new PolarisedArmourFeature(); }
}
