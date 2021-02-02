package es.menasoft.recipe.repository;

import es.menasoft.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void findByDescriptionCup() {

        // When
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Cup");

        // Then
        assertTrue(teaspoon.isPresent());
        assertEquals("Cup", teaspoon.get().getDescription());
    }

    @Test
    void findByDescriptionTeaspoon() {

        // When
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

        // Then
        assertTrue(teaspoon.isPresent());
        assertEquals("Teaspoon", teaspoon.get().getDescription());
    }
}