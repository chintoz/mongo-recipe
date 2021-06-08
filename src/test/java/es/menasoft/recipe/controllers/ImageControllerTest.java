package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.controllers.handler.ControllerExceptionHandler;
import es.menasoft.recipe.service.ImageService;
import es.menasoft.recipe.service.RecipeService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    ImageController imageController;

    MockMvc mockMvc;

    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(recipeService, imageService);
    }

    @Test
    @SneakyThrows
    void loadImageForm() {

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        when(recipeService.findCommandById(eq(firstRecipeId.toString()))).thenReturn(RecipeCommand.builder().id(firstRecipeId.toString()).build());

        mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(eq(firstRecipeId.toString()));
    }

    @Test
    @SneakyThrows
    void handleImagePost() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Recipe App TXT content".getBytes());

        mockMvc.perform(multipart("/recipe/" + firstRecipeId.toString() + "/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/" + firstRecipeId.toString() + "/show"));

        verify(imageService, times(1)).saveImageFile(eq(firstRecipeId.toString()), any());
    }

    @Test
    @SneakyThrows
    public void renderImageFromDb() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        byte[] content = "fake Image Test".getBytes();
        when(recipeService.findCommandById(eq(firstRecipeId.toString()))).thenReturn(RecipeCommand.builder().id(firstRecipeId.toString())
                .image(ArrayUtils.toObject(content)).build());

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/" + firstRecipeId.toString() + "/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(content.length, responseBytes.length);
    }
}