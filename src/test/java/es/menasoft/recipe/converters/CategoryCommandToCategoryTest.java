package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.CategoryCommand;
import es.menasoft.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        Category category = converter.convert(CategoryCommand.builder().id("1").description("Description").build());

        assertNotNull(category);
        assertEquals("1", category.getId());
        assertEquals("Description", category.getDescription());
    }
}