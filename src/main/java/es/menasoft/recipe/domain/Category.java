package es.menasoft.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Category {

    @Id
    private String id;
    private String description;
    @Builder.Default
    private Set<Recipe> recipes = new HashSet<>();

}
