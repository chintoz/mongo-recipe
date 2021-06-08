package es.menasoft.recipe.commands;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryCommand {

    private String id;
    private String description;

}
