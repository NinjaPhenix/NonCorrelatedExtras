package ninjaphenix.noncorrelatedextras.features.config;

import ninjaphenix.noncorrelatedextras.core.FeatureConfig;
import ninjaphenix.noncorrelatedextras.features.PolarisedIronFeature;

public class PolarisedIronFeatureConfig extends FeatureConfig<PolarisedIronFeature>
{
    public PolarisedIronFeatureConfig(Boolean enabled) { super(enabled); }

    @Override
    public PolarisedIronFeature getFeature() { return new PolarisedIronFeature(); }
}
