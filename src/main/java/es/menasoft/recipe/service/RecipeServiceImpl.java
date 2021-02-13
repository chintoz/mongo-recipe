package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.converters.RecipeCommandToRecipe;
import es.menasoft.recipe.converters.RecipeToRecipeCommand;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe recipe = recipeRepository.save(recipeCommandToRecipe.convert(command));
        log.debug("Recipe saved with Id: " + recipe.getId());
        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.map(recipeToRecipeCommand::convert).stream().findFirst().orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
