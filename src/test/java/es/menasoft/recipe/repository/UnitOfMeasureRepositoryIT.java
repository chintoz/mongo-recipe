package es.menasoft.recipe.repository;

import es.menasoft.recipe.BaseTestIT;
import es.menasoft.recipe.bootstrap.DataLoader;
import es.menasoft.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitOfMeasureRepositoryIT extends BaseTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        if (unitOfMeasureRepository.count() == 0) {
            dataLoader.loadUom();
        }
    }

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