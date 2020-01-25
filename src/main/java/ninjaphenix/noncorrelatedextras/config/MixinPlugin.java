package ninjaphenix.noncorrelatedextras.config;

import ninjaphenix.noncorrelatedextras.core.FeatureManager;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin
{
    private static HashMap<String, Boolean> enabledMixins;

    public MixinPlugin() {}

    @Override
    public void onLoad(String mixinPackage) { if(enabledMixins == null) enabledMixins = FeatureManager.getMixinMap(); }

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        return enabledMixins.getOrDefault(mixinClassName.substring(mixinClassName.lastIndexOf('.') + 1), Boolean.FALSE);
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
