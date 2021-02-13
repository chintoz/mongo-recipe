package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.*;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
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

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).build()));

        Recipe recipe = recipeService.findById(1L);

        assertNotNull(recipe);
        assertEquals(1, recipe.getId());
        verify(recipeRepository).findById(eq(1L));

    }

    @Test
    void findByNotExistingId() {

        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recipeService.findById(1L));
        verify(recipeRepository).findById(eq(1L));

    }

    @Test
    void saveRecipeCommand() {
        when(recipeRepository.save(any())).thenReturn(Recipe.builder().id(1L).build());

        RecipeCommand command = recipeService.saveRecipeCommand(RecipeCommand.builder().id(1L).build());

        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(recipeRepository, times(1)).save(any());

    }

    @Test
    void findCommandByExistingId() {

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(Recipe.builder().id(1L).build()));

        RecipeCommand command = recipeService.findCommandById(1L);

        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(recipeRepository, times(1)).findById(1L);

    }

    @Test
    void findCommandByNotExistingId() {

        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recipeService.findCommandById(1L));
        verify(recipeRepository, times(1)).findById(1L);

    }
}
