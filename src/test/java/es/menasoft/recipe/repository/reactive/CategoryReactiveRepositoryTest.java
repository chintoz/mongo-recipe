package es.menasoft.recipe.repository.reactive;

import es.menasoft.recipe.BaseTestIT;
import es.menasoft.recipe.domain.Category;
import es.menasoft.recipe.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryReactiveRepositoryTest extends BaseTestIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    public void reactiveCount() {

        // when
        categoryRepository.save(Category.builder().build());

        // then
        assertEquals(categoryReactiveRepository.count().block(), 1L);
    }

}