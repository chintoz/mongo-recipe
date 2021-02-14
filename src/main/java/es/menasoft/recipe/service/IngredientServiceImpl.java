package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.converters.IngredientCommandToIngredient;
import es.menasoft.recipe.converters.IngredientToIngredientCommand;
import es.menasoft.recipe.domain.Ingredient;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientCommand.getId())).findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientOptional.get();
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("Unit of Measure not found")));

        } else {
            recipe.addingIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        return savedRecipe.getIngredients().stream().filter(i -> i.getId().equals(ingredientCommand.getId()))
                .map(ingredientToIngredientCommand::convert)
                .findFirst().orElseThrow(() -> new RuntimeException("Ingredient not found"));

    }
}
