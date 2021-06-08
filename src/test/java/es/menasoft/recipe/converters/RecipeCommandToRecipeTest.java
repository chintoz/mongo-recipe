package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.CategoryCommand;
import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.NotesCommand;
import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.domain.Difficulty;
import es.menasoft.recipe.domain.Recipe;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    RecipeCommandToRecipe converter;
    ObjectId firstRecipeId = new ObjectId();

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes(), new CategoryCommandToCategory());
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {

        Recipe recipe = converter.convert(RecipeCommand.builder()
                .id(firstRecipeId.toString()).description("Description").prepTime(1).cookTime(1)
                .servings(1).source("Source").url("URL").difficulty(Difficulty.EASY)
                .directions("Directions")
                .notes(NotesCommand.builder().build())
                .categories(Set.of(CategoryCommand.builder().build()))
                .ingredients(Set.of(IngredientCommand.builder().build()))
                .build());

        assertNotNull(recipe);
        assertEquals(firstRecipeId, recipe.getId());
        assertEquals("Description", recipe.getDescription());
        assertEquals(1, recipe.getPrepTime());
        assertEquals(1, recipe.getCookTime());
        assertEquals(1, recipe.getServings());
        assertEquals("Source", recipe.getSource());
        assertEquals("URL", recipe.getUrl());
        assertEquals("Directions", recipe.getDirections());
        assertEquals(Difficulty.EASY, recipe.getDifficulty());
        assertNotNull(recipe.getNotes());
        assertEquals(1, recipe.getCategories().size());
        assertEquals(1, recipe.getIngredients().size());

    }
}