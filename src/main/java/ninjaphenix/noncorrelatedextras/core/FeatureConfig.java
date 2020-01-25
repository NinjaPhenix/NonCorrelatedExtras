package ninjaphenix.noncorrelatedextras.core;

import java.util.HashMap;

public abstract class FeatureConfig<T extends Feature>
{
	private final Boolean enabled;

	protected FeatureConfig(Boolean enabled) { this.enabled = enabled; }

	public Boolean isEnabled() { return enabled; }

	public abstract T getFeature();

	public HashMap<String, Boolean> getMixins() { return new HashMap<>(); }
}
