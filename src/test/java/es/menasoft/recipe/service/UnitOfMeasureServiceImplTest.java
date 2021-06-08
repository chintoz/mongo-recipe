package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import es.menasoft.recipe.domain.UnitOfMeasure;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listAll() {

        when(unitOfMeasureRepository.findAll()).thenReturn(List.of(UnitOfMeasure.builder().id("1").build(), UnitOfMeasure.builder().id("2").build()));

        List<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAll();

        assertNotNull(unitOfMeasureCommands);
        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();

    }
}
