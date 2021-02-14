package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;

import java.util.List;

public interface UnitOfMeasureService {

    List<UnitOfMeasureCommand> listAll();
}
