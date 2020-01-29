package ninjaphenix.noncorrelatedextras.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Mixin(ArmorFeatureRenderer.class)
@Environment(EnvType.CLIENT)
public class PolarizedArmorTextureFix
{
	@ModifyArg(method = "getArmorTexture(Lnet/minecraft/item/ArmorItem;ZLjava/lang/String;)Lnet/minecraft/util/Identifier;",
			at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"), index = 0)
	private Object getArmorTexture(Object val, Function<String, Identifier> newID)
	{
		if (val instanceof String) { if (((String) val).contains("polarized_iron")) { return "noncorrelatedextras:" + val; } }
		return val;
	}
}
