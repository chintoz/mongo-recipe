package es.menasoft.recipe.service;

import es.menasoft.recipe.domain.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();
}
