package es.menasoft.recipe.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnitOfMeasureCommand {

    private Long id;
    private String description;

}
