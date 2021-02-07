package es.menasoft.recipe.service;

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
        recipeService = new RecipeServiceImpl(recipeRepository);
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
}
