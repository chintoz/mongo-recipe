package es.menasoft.recipe.service;

import es.menasoft.recipe.BaseTestIT;
import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.RecipeCommandToRecipe;
import es.menasoft.recipe.converters.RecipeToRecipeCommand;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeServiceImplIT extends BaseTestIT {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Test
    void saveRecipeCommand() {

        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe  = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        //when
        recipeCommand.setDescription("New Description");
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals("New Description", savedRecipeCommand.getDescription());
        assertEquals(recipeCommand.getId(), savedRecipeCommand.getId());
        assertEquals(recipeCommand.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipeCommand.getIngredients().size(), savedRecipeCommand.getIngredients().size());

    }

}