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
import es.menasoft.recipe.repository.reactive.RecipeReactiveRepository;
import es.menasoft.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    IngredientService ingredientService;

    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeReactiveRepository, new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand())
                ,new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), unitOfMeasureReactiveRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {

        Recipe recipe = Recipe.builder().id(firstRecipeId).build();
        recipe.setIngredients(Set.of(Ingredient.builder().id("1").build()));
        when(recipeReactiveRepository.findById(eq(firstRecipeId))).thenReturn(Mono.just(recipe));

        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(firstRecipeId.toString(), "1").block();

        assertNotNull(ingredient);
        assertEquals("1", ingredient.getId());
        verify(recipeReactiveRepository, times(1)).findById(eq(firstRecipeId));
    }

    @Test
    @Disabled
    void findByRecipeIdAndIngredientIdNotFound() {

        Recipe recipe = Recipe.builder().id(firstRecipeId).build();
        when(recipeReactiveRepository.findById(eq(firstRecipeId))).thenReturn(Mono.just(recipe));

        assertThrows(RuntimeException.class,  () -> ingredientService.findByRecipeIdAndIngredientId(firstRecipeId.toString(), "1"));

        verify(recipeReactiveRepository, times(1)).findById(eq(firstRecipeId));
    }


    @Test
    void saveIngredientCommand() {
        Recipe recipe = Recipe.builder().id(firstRecipeId)
                .ingredients(Set.of(Ingredient.builder().id("1").uom(UnitOfMeasure.builder().id("1").build()).build())).build();
        when(recipeReactiveRepository.findById(eq(firstRecipeId))).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));
        when(unitOfMeasureReactiveRepository.findById("1")).thenReturn(Mono.just(UnitOfMeasure.builder().id("1").build()));

        IngredientCommand ingredient = ingredientService.saveIngredientCommand(IngredientCommand.builder()
                .id("1").recipeId(firstRecipeId.toString()).description("Description").unitOfMeasure(UnitOfMeasureCommand.builder().id("1").build()).build()).block();

        assertNotNull(ingredient);
        verify(recipeReactiveRepository, times(1)).findById(eq(firstRecipeId));
        verify(recipeReactiveRepository, times(1)).save(any());
        verify(unitOfMeasureReactiveRepository, times(1)).findById(eq("1"));

    }

    @Test
    void deleteByRecipeIdAndIngredientId() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id("1").uom(UnitOfMeasure.builder().id("1").build()).build());
        Recipe recipe = Recipe.builder().id(firstRecipeId).ingredients(ingredients).build();
        when(recipeReactiveRepository.findById(eq(firstRecipeId))).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn( Mono.just(Recipe.builder().id(firstRecipeId).build()));

        ingredientService.deleteByRecipeIdAndIngredientId(firstRecipeId.toString(), "1");

        verify(recipeReactiveRepository, times(1)).findById(eq(firstRecipeId));
        verify(recipeReactiveRepository, times(1)).save(any());
    }
}