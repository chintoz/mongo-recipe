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
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
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

    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand())
                ,new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {

        Recipe recipe = Recipe.builder().id(firstRecipeId).build();
        recipe.setIngredients(Set.of(Ingredient.builder().id("1").recipe(recipe).build()));
        when(recipeRepository.findById(eq(firstRecipeId))).thenReturn(Optional.of(recipe));

        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(firstRecipeId.toString(), "1");

        assertNotNull(ingredient);
        assertEquals("1", ingredient.getId());
        assertEquals(firstRecipeId.toString(), ingredient.getRecipeId());
        verify(recipeRepository, times(1)).findById(eq(firstRecipeId));
    }

    @Test
    void findByRecipeIdAndIngredientIdNotFound() {

        Recipe recipe = Recipe.builder().id(firstRecipeId).build();
        when(recipeRepository.findById(eq(firstRecipeId))).thenReturn(Optional.of(recipe));

        assertThrows(RuntimeException.class,  () -> ingredientService.findByRecipeIdAndIngredientId(firstRecipeId.toString(), "1"));

        verify(recipeRepository, times(1)).findById(eq(firstRecipeId));
    }


    @Test
    void saveIngredientCommand() {
        Recipe recipe = Recipe.builder().id(firstRecipeId)
                .ingredients(Set.of(Ingredient.builder().id("1").uom(UnitOfMeasure.builder().id("1").build()).build())).build();
        when(recipeRepository.findById(eq(firstRecipeId))).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);
        when(unitOfMeasureRepository.findById("1")).thenReturn(Optional.of(UnitOfMeasure.builder().id("1").build()));

        IngredientCommand ingredient = ingredientService.saveIngredientCommand(IngredientCommand.builder()
                .id("1").recipeId(firstRecipeId.toString()).description("Description").unitOfMeasure(UnitOfMeasureCommand.builder().id("1").build()).build());

        assertNotNull(ingredient);
        verify(recipeRepository, times(1)).findById(eq(firstRecipeId));
        verify(recipeRepository, times(1)).save(any());
        verify(unitOfMeasureRepository, times(1)).findById(eq("1"));

    }

    @Test
    void deleteByRecipeIdAndIngredientId() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id("1").uom(UnitOfMeasure.builder().id("1").build()).build());
        Recipe recipe = Recipe.builder().id(firstRecipeId).ingredients(ingredients).build();
        when(recipeRepository.findById(eq(firstRecipeId))).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn( Recipe.builder().id(firstRecipeId).build());

        ingredientService.deleteByRecipeIdAndIngredientId(firstRecipeId.toString(), "1");

        verify(recipeRepository, times(1)).findById(eq(firstRecipeId));
        verify(recipeRepository, times(1)).save(any());
    }
}