package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAll();
}
