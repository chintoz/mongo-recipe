package es.menasoft.recipe.repository.reactive;

import es.menasoft.recipe.BaseTestIT;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeReactiveRepositoryTest extends BaseTestIT {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
    }

    @Test
    public void reactiveCount() {

        // when
        recipeRepository.save(Recipe.builder().build());

        // then
        assertEquals(recipeReactiveRepository.count().block(), 1L);
    }

}