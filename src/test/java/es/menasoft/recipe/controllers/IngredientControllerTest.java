package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.service.IngredientService;
import es.menasoft.recipe.service.RecipeService;
import es.menasoft.recipe.service.UnitOfMeasureService;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
    }

    @Test
    @SneakyThrows
    void listIngredients() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(recipeService.findCommandById(eq(firstRecipeId.toString()))).thenReturn(RecipeCommand.builder().id(firstRecipeId.toString()).build());

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(eq(firstRecipeId.toString()));

    }

    @Test
    @SneakyThrows
    void showIngredient() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(ingredientService.findByRecipeIdAndIngredientId(eq(firstRecipeId.toString()), eq("1")))
                .thenReturn(IngredientCommand.builder().id("1").recipeId(firstRecipeId.toString()).build());

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(eq(firstRecipeId.toString()), eq("1"));
    }

    @Test
    @SneakyThrows
    void updateRecipeIngredient() {

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(ingredientService.findByRecipeIdAndIngredientId(eq(firstRecipeId.toString()), eq("1")))
                .thenReturn(IngredientCommand.builder().id("1").recipeId(firstRecipeId.toString()).build());

        when(unitOfMeasureService.listAll()).thenReturn(List.of(UnitOfMeasureCommand.builder().build()));

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(eq(firstRecipeId.toString()), eq("1"));
        verify(unitOfMeasureService, times(1)).listAll();
    }

    @Test
    @SneakyThrows
    void newRecipeIngredient() {

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(recipeService.findCommandById(eq(firstRecipeId.toString())))
                .thenReturn(RecipeCommand.builder().id(firstRecipeId.toString()).build());

        when(unitOfMeasureService.listAll()).thenReturn(List.of(UnitOfMeasureCommand.builder().build()));

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findCommandById(eq(firstRecipeId.toString()));
        verify(unitOfMeasureService, times(1)).listAll();
    }

    @Test
    @SneakyThrows
    void saveOrUpdate() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(ingredientService.saveIngredientCommand(any())).thenReturn(IngredientCommand.builder().recipeId(firstRecipeId.toString()).id("1").build());

        mockMvc.perform(post("/recipe/" + firstRecipeId.toString() + "/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("recipeId", firstRecipeId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + firstRecipeId.toString() + "/ingredient/1/show"));

        verify(ingredientService, times(1)).saveIngredientCommand(any());
    }


    @Test
    @SneakyThrows
    void deleteRecipeIngredient() {

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+ firstRecipeId.toString() + "/ingredients"));

        verify(ingredientService, times(1)).deleteByRecipeIdAndIngredientId(eq(firstRecipeId.toString()), eq("1"));
    }
}