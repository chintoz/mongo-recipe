package es.menasoft.recipe.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
