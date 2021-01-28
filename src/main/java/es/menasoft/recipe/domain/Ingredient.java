package es.menasoft.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private UnitOfMeasure uom;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(BigDecimal amount, String description, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

}
