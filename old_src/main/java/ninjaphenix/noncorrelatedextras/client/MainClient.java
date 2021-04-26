package ninjaphenix.noncorrelatedextras.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ninjaphenix.noncorrelatedextras.core.FeatureManager;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer
{
	public static final MainClient INSTANCE = new MainClient();

	@Override
	public void onInitializeClient()
	{
		FeatureManager.initialiseClient();
	}
}