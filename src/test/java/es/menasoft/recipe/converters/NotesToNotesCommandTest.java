package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.NotesCommand;
import es.menasoft.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void convertNull(){
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        NotesCommand notesCommand = converter.convert(Notes.builder()
                .id("1").recipeNotes("Notes").build());

        assertNotNull(notesCommand);
        assertEquals("1", notesCommand.getId());
        assertEquals("Notes", notesCommand.getRecipeNotes());
    }
}