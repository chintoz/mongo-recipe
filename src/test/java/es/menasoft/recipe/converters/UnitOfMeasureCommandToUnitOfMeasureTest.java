package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {

        UnitOfMeasure unitOfMeasure = converter.convert(UnitOfMeasureCommand.builder().id("1")
                .description("Description").build());

        assertNotNull(unitOfMeasure);
        assertEquals("1", unitOfMeasure.getId());
        assertEquals("Description", unitOfMeasure.getDescription());

    }

}
