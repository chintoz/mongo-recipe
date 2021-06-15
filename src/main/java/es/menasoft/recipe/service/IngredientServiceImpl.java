package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.converters.IngredientCommandToIngredient;
import es.menasoft.recipe.converters.IngredientToIngredientCommand;
import es.menasoft.recipe.domain.Ingredient;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.reactive.RecipeReactiveRepository;
import es.menasoft.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String id) {
        return recipeReactiveRepository
                .findById(new ObjectId(recipeId))
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")))
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(id))
                .elementAt(0)
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")))
                .map(ingredientToIngredientCommand::convert);
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {

        if (ingredientCommand.getId() == null || ingredientCommand.getId().isEmpty()) {
            ingredientCommand.setId(UUID.randomUUID().toString());
        }

        return recipeReactiveRepository
                .findById(new ObjectId(ingredientCommand.getRecipeId()))
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")))
                .map(recipe -> modifyIngredient(recipe, ingredientCommand))
                .flatMap(recipeReactiveRepository::save)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .elementAt(0)
                .map(ingredientToIngredientCommand::convert);
    }

    private Recipe modifyIngredient(Recipe recipe, IngredientCommand ingredientCommand) {

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientCommand.getId())).findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientOptional.get();
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUom(unitOfMeasureReactiveRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).blockOptional()
                    .orElseThrow(() -> new RuntimeException("Unit of Measure not found")));
        } else {
            recipe.addingIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        }

        return recipe;
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(String recipeId, String id) {
        recipeReactiveRepository.findById(new ObjectId(recipeId))
                .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")))
                .map(recipe -> deleteIngredient(recipe, id))
                .flatMap(recipeReactiveRepository::save)
                .subscribe();
    }

    private Recipe deleteIngredient(Recipe recipe, String ingredientId) {
        Ingredient ingredient = recipe.getIngredients().stream().filter(i -> i.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        recipe.getIngredients().remove(ingredient);
        return recipe;
    }

}
