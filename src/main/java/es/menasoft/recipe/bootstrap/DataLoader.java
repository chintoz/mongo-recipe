package es.menasoft.recipe.bootstrap;

import es.menasoft.recipe.domain.*;
import es.menasoft.recipe.repository.CategoryRepository;
import es.menasoft.recipe.repository.RecipeRepository;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.StreamSupport;

import static es.menasoft.recipe.domain.Difficulty.EASY;
import static es.menasoft.recipe.domain.Difficulty.MODERATE;
import static java.math.BigDecimal.valueOf;
import static java.util.Set.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) {

        // Metadata
        Map<String, UnitOfMeasure> unitOfMeasureMap = unitOfMeasures();
        Map<String, Category> categoryMap = categories();

        // Spicy Grilled Chicken Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setCookTime(15);
        tacosRecipe.setServings(6);
        tacosRecipe.setDifficulty(EASY);
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on " +
                "buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, " +
                "and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");

        tacosRecipe.setNotes(tacosNotes);

        tacosRecipe.setCategories(of(categoryMap.get("Mexican")));

        tacosRecipe.setIngredients(of(createIngredient(valueOf(2), "ancho chilli powder", unitOfMeasureMap.get("Tablespoon"), tacosRecipe),
                createIngredient(valueOf(1), "dried oregano", unitOfMeasureMap.get("Teaspoon"), tacosRecipe),
                createIngredient(valueOf(1), "dried cumin", unitOfMeasureMap.get("Teaspoon"), tacosRecipe),
                createIngredient(valueOf(1), "sugar", unitOfMeasureMap.get("Teaspoon"), tacosRecipe),
                createIngredient(valueOf((double)1/2), "salt", unitOfMeasureMap.get("Teaspoon"), tacosRecipe)));

        recipeRepository.save(tacosRecipe);


        // Guacamole Recipe
        Recipe guacamoleRecipe = new Recipe();

        guacamoleRecipe.setDescription("How to Make Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setDifficulty(MODERATE);
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after " +
                "handling and do not touch your eyes or the area near your eyes with your hands for several hours.");

        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.setCategories(of(categoryMap.get("Mexican")));

        guacamoleRecipe.setIngredients(of(createIngredient(valueOf(2), "ripe avocados", unitOfMeasureMap.get("Each"), guacamoleRecipe),
                createIngredient(valueOf((double)1/4), "salt", unitOfMeasureMap.get("Teaspoon"), guacamoleRecipe),
                createIngredient(valueOf(1), "fresh lime juice", unitOfMeasureMap.get("Tablespoon"), guacamoleRecipe),
                createIngredient(valueOf(2), "cilantro", unitOfMeasureMap.get("Tablespoon"), guacamoleRecipe),
                createIngredient(valueOf(1), "freshly grated black pepper", unitOfMeasureMap.get("Dash"), guacamoleRecipe)));

        recipeRepository.save(guacamoleRecipe);

    }


    private Map<String, UnitOfMeasure> unitOfMeasures() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .collect(toMap(UnitOfMeasure::getDescription, identity()));
    }

    private Map<String, Category> categories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .collect(toMap(Category::getDescription, identity()));
    }

    private Ingredient createIngredient(BigDecimal amount, String description, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(amount);
        ingredient.setDescription(description);
        ingredient.setUom(unitOfMeasure);
        ingredient.setRecipe(recipe);
        return ingredient;
    }
}
