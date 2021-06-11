package es.menasoft.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notes {

    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String recipeNotes;

}
