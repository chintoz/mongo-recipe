package es.menasoft.recipe.bootstrap;

import es.menasoft.recipe.domain.*;
import es.menasoft.recipe.repository.CategoryRepository;
import es.menasoft.recipe.repository.RecipeRepository;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.StreamSupport;

import static es.menasoft.recipe.domain.Difficulty.EASY;
import static es.menasoft.recipe.domain.Difficulty.MODERATE;
import static java.math.BigDecimal.valueOf;
import static java.util.Set.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
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
        tacosRecipe.setDirections("""
                1 Prepare a gas or charcoal grill for medium-high, direct heat.
                2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.
                Set aside to marinate while the grill heats and you prepare the rest of the toppings.


                3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.
                4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.
                Wrap warmed tortillas in a tea towel to keep them warm until serving.
                5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.


                Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm""");

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("""
                We have a family motto and it is this: Everything goes better in a tortilla.
                Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.
                Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!
                First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.
                Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!


                Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ""");

        tacosRecipe.setNotes(tacosNotes);

        tacosRecipe.setCategories(of(categoryMap.get("Mexican")));

        tacosRecipe.addingIngredient(new Ingredient(valueOf(2), "ancho chilli powder", unitOfMeasureMap.get("Tablespoon")))
                .addingIngredient(new Ingredient(valueOf(1), "dried oregano", unitOfMeasureMap.get("Teaspoon")))
                .addingIngredient(new Ingredient(valueOf(1), "dried cumin", unitOfMeasureMap.get("Teaspoon")))
                .addingIngredient(new Ingredient(valueOf(1), "sugar", unitOfMeasureMap.get("Teaspoon")))
                .addingIngredient(new Ingredient(valueOf((double) 1 / 2), "salt", unitOfMeasureMap.get("Teaspoon")));

        recipeRepository.save(tacosRecipe);

        log.info("Saved Tacos Recipe");


        // Guacamole Recipe
        Recipe guacamoleRecipe = new Recipe();

        guacamoleRecipe.setDescription("How to Make Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setDifficulty(MODERATE);
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setDirections("""
                1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon
                2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)
                3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.
                Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.
                Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.
                4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.
                Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.


                Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd""");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("""
                For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.
                Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.
                The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.
                To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.


                Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws""");
        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.setCategories(of(categoryMap.get("Mexican"), categoryMap.get("Fast Food")));

        guacamoleRecipe.addingIngredient(new Ingredient(valueOf(2), "ripe avocados", unitOfMeasureMap.get("Each")))
                .addingIngredient(new Ingredient(valueOf((double) 1 / 4), "salt", unitOfMeasureMap.get("Teaspoon")))
                .addingIngredient(new Ingredient(valueOf(1), "fresh lime juice", unitOfMeasureMap.get("Tablespoon")))
                .addingIngredient(new Ingredient(valueOf(2), "cilantro", unitOfMeasureMap.get("Tablespoon")))
                .addingIngredient(new Ingredient(valueOf(1), "freshly grated black pepper", unitOfMeasureMap.get("Dash")));

        recipeRepository.save(guacamoleRecipe);

        log.info("Saved Guacamole Recipe");

    }


    private Map<String, UnitOfMeasure> unitOfMeasures() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .collect(toMap(UnitOfMeasure::getDescription, identity()));
    }

    private Map<String, Category> categories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .collect(toMap(Category::getDescription, identity()));
    }

}
