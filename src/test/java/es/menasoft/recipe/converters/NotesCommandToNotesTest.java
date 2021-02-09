package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.NotesCommand;
import es.menasoft.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    NotesCommandToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convert() {
        Notes notes = converter.convert(NotesCommand.builder().id(1L).recipeNotes("Notes").build());

        assertNotNull(notes);
        assertEquals(1L, notes.getId());
        assertEquals("Notes", notes.getRecipeNotes());
    }
}