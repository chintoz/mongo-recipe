package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.domain.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findCommandById(String id);

    void deleteById(String id);
}
