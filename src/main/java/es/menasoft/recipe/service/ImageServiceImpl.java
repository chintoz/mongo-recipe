package es.menasoft.recipe.service;

import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional
    @SneakyThrows
    public void saveImageFile(Long recipeId, MultipartFile file) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        Recipe recipe = recipeOptional.get();
        recipe.setImage(ArrayUtils.toObject(file.getBytes()));

        recipeRepository.save(recipe);

        log.info("Received file for recipe {}", recipeId);
    }
}
