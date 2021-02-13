package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.converters.IngredientToIngredientCommand;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        return recipe.get().getIngredients().stream()
                .filter(i -> i.getId().equals(id))
                .map(ingredientToIngredientCommand::convert)
                .findFirst().orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }
}
