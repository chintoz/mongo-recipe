package es.menasoft.recipe.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotesCommand {

    private String id;
    private String recipeNotes;

}
