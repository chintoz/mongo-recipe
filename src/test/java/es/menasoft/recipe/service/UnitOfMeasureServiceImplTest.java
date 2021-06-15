package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import es.menasoft.recipe.domain.UnitOfMeasure;
import es.menasoft.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listAll() {

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(UnitOfMeasure.builder().id("1").build(), UnitOfMeasure.builder().id("2").build()));

        Flux<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAll();

        assertNotNull(unitOfMeasureCommands);
        assertEquals(2, unitOfMeasureCommands.collectList().block().size());
        verify(unitOfMeasureReactiveRepository, times(1)).findAll();

    }
}
