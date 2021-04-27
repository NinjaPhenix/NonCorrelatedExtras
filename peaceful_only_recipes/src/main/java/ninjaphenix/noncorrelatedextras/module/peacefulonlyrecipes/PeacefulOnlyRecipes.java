package ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.recipe.PeacefulOnlyShapedRecipe;
import ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.recipe.PeacefulOnlyShapelessRecipe;
import ninjaphenix.noncorrelatedextras.module.peacefulonlyrecipes.recipe.RecipeGroupAccessor;

public class PeacefulOnlyRecipes implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation("noncorrelatedextras", "peaceful_only_crafting_shaped"), new RecipeSerializer<PeacefulOnlyShapedRecipe>() {
            @Override
            public PeacefulOnlyShapedRecipe fromJson(final ResourceLocation ID, final JsonObject JSON) {
                return createRecipe(RecipeSerializer.SHAPED_RECIPE.fromJson(ID, JSON));
            }

            @Override
            public PeacefulOnlyShapedRecipe fromNetwork(final ResourceLocation ID, final FriendlyByteBuf BUFFER) {
                return createRecipe(RecipeSerializer.SHAPED_RECIPE.fromNetwork(ID, BUFFER));
            }

            @Override
            public void toNetwork(final FriendlyByteBuf BUFFER, final PeacefulOnlyShapedRecipe RECIPE) {
                RecipeSerializer.SHAPED_RECIPE.toNetwork(BUFFER, RECIPE);
            }
        });

        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation("noncorrelatedextras", "peaceful_only_crafting_shapeless"), new RecipeSerializer<PeacefulOnlyShapelessRecipe>() {
            @Override
            public PeacefulOnlyShapelessRecipe fromJson(final ResourceLocation ID, final JsonObject JSON) {
                return createRecipe(RecipeSerializer.SHAPELESS_RECIPE.fromJson(ID, JSON));
            }

            @Override
            public PeacefulOnlyShapelessRecipe fromNetwork(final ResourceLocation ID, final FriendlyByteBuf BUFFER) {
                return createRecipe(RecipeSerializer.SHAPELESS_RECIPE.fromNetwork(ID, BUFFER));
            }

            @Override
            public void toNetwork(final FriendlyByteBuf BUFFER, final PeacefulOnlyShapelessRecipe RECIPE) {
                RecipeSerializer.SHAPELESS_RECIPE.toNetwork(BUFFER, RECIPE);
            }
        });
    }

    private static PeacefulOnlyShapedRecipe createRecipe(ShapedRecipe recipe) {
        return new PeacefulOnlyShapedRecipe(recipe.getId(), ((RecipeGroupAccessor) recipe).nceGetGroup(), recipe.getWidth(),
                                            recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
    }

    private static PeacefulOnlyShapelessRecipe createRecipe(ShapelessRecipe recipe) {
        return new PeacefulOnlyShapelessRecipe(recipe.getId(), ((RecipeGroupAccessor) recipe).nceGetGroup(), recipe.getResultItem(), recipe.getIngredients());
    }
}
