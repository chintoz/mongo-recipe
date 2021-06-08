package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.RecipeCommandToRecipe;
import es.menasoft.recipe.converters.RecipeToRecipeCommand;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.exception.NotFoundException;
import es.menasoft.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    @Override
    public List<Recipe> findAll() {
        log.info("find all recipes");
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Recipe findById(String id) {
        return recipeRepository.findById(new ObjectId(id)).orElseThrow(() -> new NotFoundException("Recipe not found for id value: " + id.toString()));
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        @SuppressWarnings("ConstantConditions") Recipe recipe = recipeRepository.save(recipeCommandToRecipe.convert(command));
        log.debug("Recipe saved with Id: " + recipe.getId());
        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    public RecipeCommand findCommandById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(new ObjectId(id));
        return recipe.map(recipeToRecipeCommand::convert).stream().findFirst().orElseThrow(() -> new NotFoundException("Recipe not found for id value: " + id.toString()));
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(new ObjectId(id));
    }
}
