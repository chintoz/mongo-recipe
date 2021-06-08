package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.*;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.exception.NotFoundException;
import es.menasoft.recipe.repository.RecipeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,
                new RecipeToRecipeCommand(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                        new NotesToNotesCommand(), new CategoryToCategoryCommand()),
                new RecipeCommandToRecipe(new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                        new NotesCommandToNotes(), new CategoryCommandToCategory()));
    }

    @Test
    void findAll() {
        // given
        when(recipeRepository.findAll()).thenReturn(Set.of(Recipe.builder().build()));

        // when
        List<Recipe> recipes = recipeService.findAll();

        // then
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    void findByExistingId() {

        when(recipeRepository.findById(firstRecipeId)).thenReturn(Optional.of(Recipe.builder().id(firstRecipeId).build()));

        Recipe recipe = recipeService.findById(firstRecipeId.toString());

        assertNotNull(recipe);
        assertEquals(firstRecipeId, recipe.getId());
        verify(recipeRepository).findById(eq(firstRecipeId));

    }

    @Test
    void findByNotExistingId() {

        when(recipeRepository.findById(firstRecipeId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recipeService.findById(firstRecipeId.toString()));
        verify(recipeRepository).findById(eq(firstRecipeId));

    }

    @Test
    void saveRecipeCommand() {
        when(recipeRepository.save(any())).thenReturn(Recipe.builder().id(firstRecipeId).build());

        RecipeCommand command = recipeService.saveRecipeCommand(RecipeCommand.builder().id(firstRecipeId.toString()).build());

        assertNotNull(command);
        assertEquals(firstRecipeId.toString(), command.getId());
        verify(recipeRepository, times(1)).save(any());

    }

    @Test
    void findCommandByExistingId() {

        when(recipeRepository.findById(firstRecipeId)).thenReturn(Optional.of(Recipe.builder().id(firstRecipeId).build()));

        RecipeCommand command = recipeService.findCommandById(firstRecipeId.toString());

        assertNotNull(command);
        assertEquals(firstRecipeId.toString(), command.getId());
        verify(recipeRepository, times(1)).findById(firstRecipeId);

    }

    @Test
    void findCommandByNotExistingId() {

        when(recipeRepository.findById(firstRecipeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.findCommandById(firstRecipeId.toString()));
        verify(recipeRepository, times(1)).findById(firstRecipeId);

    }

    @Test
    void deleteById() {
        recipeService.deleteById(firstRecipeId.toString());
        verify(recipeRepository,times(1)).deleteById(eq(firstRecipeId));
    }
}
