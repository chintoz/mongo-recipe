package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String id);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteByRecipeIdAndIngredientId(String recipeId, String id);
}
