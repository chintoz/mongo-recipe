package es.menasoft.recipe.controllers;

import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getIndexPage() {
        // Given
        List<Recipe> recipes = of(Recipe.builder().description("Recipe 1").build(),
                Recipe.builder().description("Recipe 2").build());
        when(recipeService.findAll()).thenReturn(recipes);

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        // When
        String indexPage = indexController.getIndexPage(model);

        // Then
        assertEquals("index", indexPage);
        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        List<Recipe> recipesResult = argumentCaptor.getValue();
        assertEquals(recipes, recipesResult);
    }

}
