package es.menasoft.recipe.commands;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryCommand {

    private Long id;
    private String description;

}
