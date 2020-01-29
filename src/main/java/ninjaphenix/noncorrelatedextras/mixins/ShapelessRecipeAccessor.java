package ninjaphenix.noncorrelatedextras.mixins;

import net.minecraft.recipe.ShapelessRecipe;
import ninjaphenix.noncorrelatedextras.recipe.RecipeGroupAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeAccessor implements RecipeGroupAccessor
{
	@Shadow @Final private String group;

	@Override
	public String getGroupA() { return group; }
}
