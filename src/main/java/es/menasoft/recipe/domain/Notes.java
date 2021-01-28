package es.menasoft.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
