package ninjaphenix.noncorrelatedextras.config;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin
{
    private final HashMap<String, String> featureMixinMap;
    public MixinPlugin()
    {
        featureMixinMap = new HashMap<>();
        featureMixinMap.put("ninjaphenix.noncorrelatedextras.mixins.EnchantableShear", "enchantable_shears");
        featureMixinMap.put("ninjaphenix.noncorrelatedextras.mixins.CreeperNoGrief", "creepers_no_grief");
    }
    @Override
    public void onLoad(String mixinPackage)
    {
        if(Config.INSTANCE == null)
            Config.initialize();
    }

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        if(mixinClassName.equals("ninjaphenix.noncorrelatedextras.mixins.MagnetTrinketCompat") && Config.INSTANCE.isFeatureEnabled("magnet")) return FabricLoader.getInstance().isModLoaded("trinkets");
        if(mixinClassName.equals("ninjaphenix.noncorrelatedextras.mixins.TrinketModFix") && Config.INSTANCE.isFeatureEnabled("magnet")) return FabricLoader.getInstance().isModLoaded("trinkets");
        return Config.INSTANCE.isFeatureEnabled(featureMixinMap.getOrDefault(mixinClassName, null));
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
