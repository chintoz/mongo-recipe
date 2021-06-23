package es.menasoft.recipe.commands;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCommand {

    private String id;
    private String recipeId;
    @NotBlank
    private String description;
    @NotNull
    @Min(1)
    private BigDecimal amount;
    @NotNull
    private UnitOfMeasureCommand unitOfMeasure;

}
