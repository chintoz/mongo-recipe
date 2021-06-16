package es.menasoft.recipe.service;

import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    @Override
    @SneakyThrows
    public void saveImageFile(String recipeId, MultipartFile file) {

        Recipe recipe = recipeReactiveRepository.findById(new ObjectId(recipeId))
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")))
                .map(r -> {
                    r.setImage(getBytes(file));
                    return r;
                })
                .block();
        recipe = recipeReactiveRepository.save(recipe).block();
        log.info("Received file for recipe {}", recipe.toString());
    }

    @SneakyThrows
    private byte[] getBytes(MultipartFile file) {
        return file.getBytes();
    }
}
