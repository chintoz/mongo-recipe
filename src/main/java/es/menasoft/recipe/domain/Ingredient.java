package es.menasoft.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Ingredient {

    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;

    public Ingredient() {
    }

    public Ingredient(BigDecimal amount, String description, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

}
