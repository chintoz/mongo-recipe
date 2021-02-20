package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.domain.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand(), new CategoryToCategoryCommand());
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {

        RecipeCommand recipeCommand = converter.convert(Recipe.builder()
                .id(1L).description("Description").prepTime(1).cookTime(1)
                .servings(1).source("Source").url("URL").difficulty(Difficulty.EASY)
                .directions("Directions")
                .notes(Notes.builder().build())
                .categories(Set.of(Category.builder().build()))
                .ingredients(Set.of(Ingredient.builder().build()))
                .image(ArrayUtils.toObject("Data".getBytes()))
                .build());

        assertNotNull(recipeCommand);
        assertEquals(1L, recipeCommand.getId());
        assertEquals("Description", recipeCommand.getDescription());
        assertEquals(1, recipeCommand.getPrepTime());
        assertEquals(1, recipeCommand.getCookTime());
        assertEquals(1, recipeCommand.getServings());
        assertEquals("Source", recipeCommand.getSource());
        assertEquals("URL", recipeCommand.getUrl());
        assertEquals("Directions", recipeCommand.getDirections());
        assertEquals(Difficulty.EASY, recipeCommand.getDifficulty());
        assertNotNull(recipeCommand.getNotes());
        assertEquals(1, recipeCommand.getCategories().size());
        assertEquals(1, recipeCommand.getIngredients().size());
        assertEquals("Data".getBytes().length, ArrayUtils.toPrimitive(recipeCommand.getImage()).length);

    }
}