package es.menasoft.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    @EqualsAndHashCode.Exclude
    private Set<Recipe> recipes = new HashSet<>();

}
