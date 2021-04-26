package ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.mixins;

import net.minecraft.world.item.crafting.ShapelessRecipe;
import ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.recipe.RecipeGroupAccessor;
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
