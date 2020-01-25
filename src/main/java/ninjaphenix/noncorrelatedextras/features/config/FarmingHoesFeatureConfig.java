package ninjaphenix.noncorrelatedextras.features.config;

import ninjaphenix.noncorrelatedextras.core.FeatureConfig;
import ninjaphenix.noncorrelatedextras.features.FarmingHoesFeature;

public class FarmingHoesFeatureConfig extends FeatureConfig<FarmingHoesFeature>
{
	public FarmingHoesFeatureConfig(Boolean enabled) { super(enabled); }

	@Override
	public FarmingHoesFeature getFeature() { return new FarmingHoesFeature(); }
}
