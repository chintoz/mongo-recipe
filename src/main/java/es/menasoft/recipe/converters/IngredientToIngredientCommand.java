package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        return IngredientCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .unitOfMeasure(unitOfMeasureToUnitOfMeasureCommand.convert(source.getUom()))
                .build();

    }
}
