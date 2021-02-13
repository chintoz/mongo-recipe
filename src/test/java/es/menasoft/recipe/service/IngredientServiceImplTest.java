package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.converters.IngredientToIngredientCommand;
import es.menasoft.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import es.menasoft.recipe.domain.Ingredient;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    void findByRecipeIdAndIngredientId() {

        Recipe recipe = Recipe.builder().id(1L).build();
        recipe.setIngredients(Set.of(Ingredient.builder().id(1L).recipe(recipe).build()));
        when(recipeRepository.findById(eq(1L))).thenReturn(Optional.of(recipe));

        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);

        assertNotNull(ingredient);
        assertEquals(1L, ingredient.getId());
        assertEquals(1L, ingredient.getRecipeId());
        verify(recipeRepository, times(1)).findById(eq(1L));
    }

    @Test
    void findByRecipeIdAndIngredientIdNotFound() {

        Recipe recipe = Recipe.builder().id(1L).build();
        when(recipeRepository.findById(eq(1L))).thenReturn(Optional.of(recipe));

        assertThrows(RuntimeException.class,  () -> ingredientService.findByRecipeIdAndIngredientId(1L, 1L));

        verify(recipeRepository, times(1)).findById(eq(1L));
    }
}