package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.exception.NotFoundException;
import es.menasoft.recipe.service.RecipeService;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    ObjectId firstRecipeId = new ObjectId();
    ObjectId secondRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    @SneakyThrows
    void testShowRecipe() {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findById(firstRecipeId.toString())).thenReturn(Mono.just(Recipe.builder().id(firstRecipeId).build()));

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    @SneakyThrows
    void testShowRecipeNotFound() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findById(firstRecipeId.toString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    @SneakyThrows
    void testNewRecipe() {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    @SneakyThrows
    void testUpdateRecipe() {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.findCommandById(firstRecipeId.toString())).thenReturn(Mono.just(RecipeCommand.builder().id(firstRecipeId.toString()).build()));

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    @SneakyThrows
    void saveOrUpdate() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        RecipeCommand command = RecipeCommand.builder().id(secondRecipeId.toString()).build();
        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "Description")
                .param("directions", "Directions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + secondRecipeId.toString() + "/show"));
    }

    @Test
    @SneakyThrows
    void saveOrUpdateWithFail() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        RecipeCommand command = RecipeCommand.builder().id(secondRecipeId.toString()).build();
        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "Description"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    @SneakyThrows
    void deleteRecipe() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() +"/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(eq(firstRecipeId.toString()));
    }
}