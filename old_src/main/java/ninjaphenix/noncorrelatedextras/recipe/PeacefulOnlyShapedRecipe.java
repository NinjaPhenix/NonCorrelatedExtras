package ninjaphenix.noncorrelatedextras.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class PeacefulOnlyShapedRecipe extends ShapedRecipe
{
	public PeacefulOnlyShapedRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> ingredients, ItemStack output)
	{
		super(id, group, width, height, ingredients, output);
	}

	@Override
	public boolean matches(CraftingInventory craftingInventory, World world)
	{
		if (world.getDifficulty() == Difficulty.PEACEFUL) { return super.matches(craftingInventory, world); }
		return false;
	}
}
