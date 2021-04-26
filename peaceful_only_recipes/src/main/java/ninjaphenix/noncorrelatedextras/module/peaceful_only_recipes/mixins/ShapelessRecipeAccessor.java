package ninjaphenix.noncorrelatedextras.module.peaceful_only_recipes.mixins;

import net.minecraft.world.item.crafting.ShapelessRecipe;
import ninjaphenix.noncorrelatedextras.module.peaceful_only_recipes.recipe.RecipeGroupAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeAccessor implements RecipeGroupAccessor {
    @Shadow
    @Final
    private String group;

    @Override
    public String nceGetGroup() {
        return group;
    }
}
