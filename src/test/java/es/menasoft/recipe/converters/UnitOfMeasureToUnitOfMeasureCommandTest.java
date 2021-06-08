package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(UnitOfMeasure.builder()
                .id("1").description("Description").build());

        assertNotNull(unitOfMeasureCommand);
        assertEquals("1", unitOfMeasureCommand.getId());
        assertEquals("Description", unitOfMeasureCommand.getDescription());


    }
}