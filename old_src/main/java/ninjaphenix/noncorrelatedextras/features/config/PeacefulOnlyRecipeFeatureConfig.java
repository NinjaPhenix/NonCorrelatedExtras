package ninjaphenix.noncorrelatedextras.features.config;

import ninjaphenix.noncorrelatedextras.core.FeatureConfig;
import ninjaphenix.noncorrelatedextras.features.PeacefulOnlyRecipeFeature;

import java.util.HashMap;

public class PeacefulOnlyRecipeFeatureConfig extends FeatureConfig<PeacefulOnlyRecipeFeature>
{
	public PeacefulOnlyRecipeFeatureConfig(Boolean enabled) { super(enabled); }

	@Override
	public PeacefulOnlyRecipeFeature getFeature() { return new PeacefulOnlyRecipeFeature(); }

	@Override
	public HashMap<String, Boolean> getMixins()
	{
		HashMap<String, Boolean> map = super.getMixins();
		map.put("ShapedRecipeAccessor", isEnabled());
		map.put("ShapelessRecipeAccessor", isEnabled());
		return map;
	}
}
