package ninjaphenix.noncorrelatedextras;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import ninjaphenix.noncorrelatedextras.core.FeatureManager;

public class Main implements ModInitializer
{
    public static final Main INSTANCE = new Main();

    private Main() {}

    public static Identifier getId(String path) { return new Identifier("noncorrelatedextras", path); }

    @Override
    public void onInitialize() { FeatureManager.initialise(); }
}
