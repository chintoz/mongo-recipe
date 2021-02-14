package es.menasoft.recipe.service;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import es.menasoft.recipe.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public List<UnitOfMeasureCommand> listAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(toList());
    }
}
