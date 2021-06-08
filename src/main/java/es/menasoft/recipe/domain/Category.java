package es.menasoft.recipe.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private String id;
    private String description;
    private Set<Recipe> recipes = new HashSet<>();

}
