package ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.mixins;

import net.minecraft.world.item.crafting.ShapedRecipe;
import ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.recipe.RecipeGroupAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeAccessor implements RecipeGroupAccessor {
    @Shadow
    @Final
    private String group;

    @Override
    public String nceGetGroup() {
        return group;
    }
}
