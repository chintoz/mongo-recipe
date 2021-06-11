package es.menasoft.recipe.repository.reactive;

import es.menasoft.recipe.BaseTestIT;
import es.menasoft.recipe.domain.UnitOfMeasure;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitOfMeasureReactiveRepositoryTest extends BaseTestIT {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureRepository.deleteAll();
    }

    @Test
    public void reactiveCount() {

        // when
        unitOfMeasureRepository.save(UnitOfMeasure.builder().build());

        // then
        assertEquals(unitOfMeasureReactiveRepository.count().block(), 1L);
    }

}