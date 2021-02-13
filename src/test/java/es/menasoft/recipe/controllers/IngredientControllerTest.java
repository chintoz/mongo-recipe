package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.service.IngredientService;
import es.menasoft.recipe.service.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService);
    }

    @Test
    @SneakyThrows
    void listIngredients() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(recipeService.findCommandById(eq(1L))).thenReturn(RecipeCommand.builder().id(1L).build());

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(eq(1L));

    }

    @Test
    @SneakyThrows
    void showIngredient() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(ingredientService.findByRecipeIdAndIngredientId(eq(1L), eq(1L)))
                .thenReturn(IngredientCommand.builder().id(1L).recipeId(1L).build());

        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(eq(1L), eq(1L));
    }
}