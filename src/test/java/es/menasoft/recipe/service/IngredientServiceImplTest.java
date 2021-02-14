package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.converters.IngredientCommandToIngredient;
import es.menasoft.recipe.converters.IngredientToIngredientCommand;
import es.menasoft.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import es.menasoft.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import es.menasoft.recipe.domain.Ingredient;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.domain.UnitOfMeasure;
import es.menasoft.recipe.repository.RecipeRepository;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
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

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand())
                ,new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), unitOfMeasureRepository);
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


    @Test
    void saveIngredientCommand() {
        Recipe recipe = Recipe.builder().id(1L)
                .ingredients(Set.of(Ingredient.builder().id(1L).uom(UnitOfMeasure.builder().id(1L).build()).build())).build();
        when(recipeRepository.findById(eq(1L))).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);
        when(unitOfMeasureRepository.findById(1L)).thenReturn(Optional.of(UnitOfMeasure.builder().id(1L).build()));

        IngredientCommand ingredient = ingredientService.saveIngredientCommand(IngredientCommand.builder()
                .id(1L).recipeId(1L).description("Description").unitOfMeasure(UnitOfMeasureCommand.builder().id(1L).build()).build());

        assertNotNull(ingredient);
        verify(recipeRepository, times(1)).findById(eq(1L));
        verify(recipeRepository, times(1)).save(any());
        verify(unitOfMeasureRepository, times(1)).findById(eq(1L));

    }
}