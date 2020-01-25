package ninjaphenix.noncorrelatedextras.features.config;

import ninjaphenix.noncorrelatedextras.core.MixinOnlyFeatureConfig;

import java.util.HashMap;

public class CreeperBreakBlocksFeatureConfig extends MixinOnlyFeatureConfig
{
	public CreeperBreakBlocksFeatureConfig(Boolean enabled) { super(enabled); }

	@Override
	public HashMap<String, Boolean> getMixins()
	{
		HashMap<String, Boolean> map = super.getMixins();
		map.put("CreeperBreakBlocks", isEnabled());
		return map;
	}
}
