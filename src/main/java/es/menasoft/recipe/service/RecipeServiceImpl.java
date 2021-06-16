package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.RecipeCommandToRecipe;
import es.menasoft.recipe.converters.RecipeToRecipeCommand;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.exception.NotFoundException;
import es.menasoft.recipe.repository.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    @Override
    public Flux<Recipe> findAll() {
        log.info("find all recipes");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(new ObjectId(id))
                .switchIfEmpty(Mono.error(new NotFoundException("Recipe not found for id value: " + id.toString())));
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeReactiveRepository.save(Objects.requireNonNull(recipeCommandToRecipe.convert(command)))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(new ObjectId(id))
            .switchIfEmpty(Mono.error(new NotFoundException("Recipe not found for id value: " + id.toString())))
            .map(recipeToRecipeCommand::convert);
    }

    @Override
    public void deleteById(String id) {
        recipeReactiveRepository.deleteById(new ObjectId(id)).subscribe();
    }
}
