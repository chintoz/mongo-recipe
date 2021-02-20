package es.menasoft.recipe.service;

import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService  imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    @SneakyThrows
    void saveImageFile() {
        // given
        MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "File Content".getBytes());
        Recipe recipe = Recipe.builder().id(1L).build();

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        // when
        imageService.saveImageFile(1L, multipartFile);

        // then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

    }
}