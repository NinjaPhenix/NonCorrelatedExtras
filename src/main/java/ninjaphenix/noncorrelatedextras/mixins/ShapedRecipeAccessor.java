package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.recipe.ShapedRecipe;
import ninjaphenix.noncorrelatedextras.recipe.RecipeGroupAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeAccessor implements RecipeGroupAccessor
{
	@Shadow @Final private String group;

	@Override
	public String getGroupA() { return group; }
}
