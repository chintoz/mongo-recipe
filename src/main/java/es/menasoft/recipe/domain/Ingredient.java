package es.menasoft.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @NotNull
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private BigDecimal amount;
    private String description;
    private UnitOfMeasure uom;
}
