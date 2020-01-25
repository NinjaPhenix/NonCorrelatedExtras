package ninjaphenix.noncorrelatedextras.core;

import ninjaphenix.noncorrelatedextras.features.EmptyFeature;

public class MixinOnlyFeatureConfig extends FeatureConfig<EmptyFeature>
{
	protected MixinOnlyFeatureConfig(Boolean enabled) { super(enabled); }

	@Override
	public EmptyFeature getFeature() { return EmptyFeature.INSTANCE; }
}
