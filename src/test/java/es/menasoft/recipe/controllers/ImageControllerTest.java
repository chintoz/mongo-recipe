package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.service.ImageService;
import es.menasoft.recipe.service.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(recipeService, imageService);
    }

    @Test
    @SneakyThrows
    void loadImageForm() {

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        when(recipeService.findCommandById(eq(1L))).thenReturn(RecipeCommand.builder().id(1L).build());

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(eq(1L));
    }

    @Test
    @SneakyThrows
    void handleImagePost() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Recipe App TXT content".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(eq(1L), any());
    }
}