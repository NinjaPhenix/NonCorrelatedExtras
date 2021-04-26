package ninjaphenix.noncorrelatedextras.features;

import com.google.gson.JsonObject;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import ninjaphenix.noncorrelatedextras.Main;
import ninjaphenix.noncorrelatedextras.core.Feature;
import ninjaphenix.noncorrelatedextras.recipe.PeacefulOnlyShapedRecipe;
import ninjaphenix.noncorrelatedextras.recipe.PeacefulOnlyShapelessRecipe;
import ninjaphenix.noncorrelatedextras.recipe.RecipeGroupAccessor;

public class PeacefulOnlyRecipeFeature extends Feature
{
	@Override
	public void initialise()
	{
		Registry.register(Registry.RECIPE_SERIALIZER, Main.getId("peaceful_only_crafting_shaped"), new RecipeSerializer<PeacefulOnlyShapedRecipe>()
		{
			@Override
			public PeacefulOnlyShapedRecipe read(Identifier id, JsonObject json) { return createRecipe(RecipeSerializer.SHAPED.read(id, json)); }

			@Override
			public PeacefulOnlyShapedRecipe read(Identifier id, PacketByteBuf buf) { return createRecipe(RecipeSerializer.SHAPED.read(id, buf)); }

			@Override
			public void write(PacketByteBuf buf, PeacefulOnlyShapedRecipe recipe) { RecipeSerializer.SHAPED.write(buf, recipe); }
		});

		Registry.register(Registry.RECIPE_SERIALIZER, Main.getId("peaceful_only_crafting_shapeless"), new RecipeSerializer<PeacefulOnlyShapelessRecipe>()
		{
			@Override
			public PeacefulOnlyShapelessRecipe read(Identifier id, JsonObject json) { return createRecipe(RecipeSerializer.SHAPELESS.read(id, json)); }

			@Override
			public PeacefulOnlyShapelessRecipe read(Identifier id, PacketByteBuf buf) { return createRecipe(RecipeSerializer.SHAPELESS.read(id, buf)); }

			@Override
			public void write(PacketByteBuf buf, PeacefulOnlyShapelessRecipe recipe) { RecipeSerializer.SHAPELESS.write(buf, recipe); }
		});
	}

	private static PeacefulOnlyShapedRecipe createRecipe(ShapedRecipe recipe)
	{
		return new PeacefulOnlyShapedRecipe(recipe.getId(), ((RecipeGroupAccessor) recipe).getGroupA(), recipe.getWidth(),
				recipe.getHeight(), recipe.getPreviewInputs(), recipe.getOutput());
	}

	private static PeacefulOnlyShapelessRecipe createRecipe(ShapelessRecipe recipe)
	{
		return new PeacefulOnlyShapelessRecipe(recipe.getId(), ((RecipeGroupAccessor) recipe).getGroupA(), recipe.getOutput(), recipe.getPreviewInputs());
	}
}
