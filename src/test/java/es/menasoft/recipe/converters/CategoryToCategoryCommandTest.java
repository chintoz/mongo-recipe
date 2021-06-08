package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.CategoryCommand;
import es.menasoft.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = converter.convert(Category.builder()
                .id("1").description("Description").build());

        assertNotNull(categoryCommand);
        assertEquals("1", categoryCommand.getId());
        assertEquals("Description", categoryCommand.getDescription());
    }
}