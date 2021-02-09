package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null) {
            return null;
        }
        return UnitOfMeasureCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
