package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        Ingredient ingredient = converter.convert(IngredientCommand.builder()
                .id("1").description("Description").amount(BigDecimal.ONE)
                .unitOfMeasure(UnitOfMeasureCommand.builder().build())
                .build());

        assertNotNull(ingredient);
        assertEquals("1", ingredient.getId());
        assertEquals("Description", ingredient.getDescription());
        assertEquals(BigDecimal.ONE, ingredient.getAmount());
        assertNotNull(ingredient.getUom());
    }
}
