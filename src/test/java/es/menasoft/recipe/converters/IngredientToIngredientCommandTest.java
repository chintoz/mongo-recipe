package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        IngredientCommand ingredientCommand = converter.convert(Ingredient.builder().id("1")
                .description("Description").amount(BigDecimal.ONE).build());

        assertNotNull(ingredientCommand);
        assertEquals("1", ingredientCommand.getId());
        assertEquals("Description", ingredientCommand.getDescription());
        assertEquals(BigDecimal.ONE, ingredientCommand.getAmount());

    }
}