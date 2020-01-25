package ninjaphenix.noncorrelatedextras.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public abstract class Feature
{
	public void initialise() {}

	@Environment(EnvType.CLIENT)
	public void initialiseClient() {}
}
