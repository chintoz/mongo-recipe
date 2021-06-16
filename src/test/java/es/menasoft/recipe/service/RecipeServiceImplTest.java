package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.*;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.exception.NotFoundException;
import es.menasoft.recipe.repository.reactive.RecipeReactiveRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeReactiveRepository,
                new RecipeToRecipeCommand(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                        new NotesToNotesCommand(), new CategoryToCategoryCommand()),
                new RecipeCommandToRecipe(new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                        new NotesCommandToNotes(), new CategoryCommandToCategory()));
    }

    @Test
    void findAll() {
        // given
        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(Recipe.builder().build()));

        // when
        List<Recipe> recipes = recipeService.findAll().collectList().block();

        // then
        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        verify(recipeReactiveRepository, times(1)).findAll();

    }

    @Test
    void findByExistingId() {

        when(recipeReactiveRepository.findById(firstRecipeId)).thenReturn(Mono.just(Recipe.builder().id(firstRecipeId).build()));

        Recipe recipe = recipeService.findById(firstRecipeId.toString()).block();

        assertNotNull(recipe);
        assertEquals(firstRecipeId, recipe.getId());
        verify(recipeReactiveRepository).findById(eq(firstRecipeId));

    }

    @Test
    void findByNotExistingId() {

        when(recipeReactiveRepository.findById(firstRecipeId)).thenReturn(Mono.empty());

        assertThrows(RuntimeException.class, () -> recipeService.findById(firstRecipeId.toString()).block());
        verify(recipeReactiveRepository).findById(eq(firstRecipeId));

    }

    @Test
    void saveRecipeCommand() {
        when(recipeReactiveRepository.save(any())).thenReturn(MonoOperator.just(Recipe.builder().id(firstRecipeId).build()));

        RecipeCommand command = recipeService.saveRecipeCommand(RecipeCommand.builder().id(firstRecipeId.toString()).build()).block();

        assertNotNull(command);
        assertEquals(firstRecipeId.toString(), command.getId());
        verify(recipeReactiveRepository, times(1)).save(any());

    }

    @Test
    void findCommandByExistingId() {

        when(recipeReactiveRepository.findById(firstRecipeId)).thenReturn(Mono.just(Recipe.builder().id(firstRecipeId).build()));

        RecipeCommand command = recipeService.findCommandById(firstRecipeId.toString()).block();

        assertNotNull(command);
        assertEquals(firstRecipeId.toString(), command.getId());
        verify(recipeReactiveRepository, times(1)).findById(firstRecipeId);

    }

    @Test
    void findCommandByNotExistingId() {

        when(recipeReactiveRepository.findById(firstRecipeId)).thenReturn(Mono.empty());

        assertThrows(NotFoundException.class, () -> recipeService.findCommandById(firstRecipeId.toString()).block());
        verify(recipeReactiveRepository, times(1)).findById(firstRecipeId);

    }

    @Test
    void deleteById() {
        when(recipeReactiveRepository.deleteById(firstRecipeId)).thenReturn(Mono.empty());
        recipeService.deleteById(firstRecipeId.toString());
        verify(recipeReactiveRepository,times(1)).deleteById(eq(firstRecipeId));
    }
}
