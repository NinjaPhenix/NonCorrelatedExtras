package ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class PeacefulOnlyShapedRecipe extends ShapedRecipe {
    public PeacefulOnlyShapedRecipe(final ResourceLocation ID, final String GROUP, final int WIDTH, final int HEIGHT,
                                    final NonNullList<Ingredient> INGREDIENTS, final ItemStack RESULT) {
        super(ID, GROUP, WIDTH, HEIGHT, INGREDIENTS, RESULT);
    }

    @Override
    public boolean matches(final CraftingContainer CRAFTING_CONTAINER, final Level LEVEL) {
        if (LEVEL.getDifficulty() == Difficulty.PEACEFUL) {
            return super.matches(CRAFTING_CONTAINER, LEVEL);
        }
        return false;
    }
}
