package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.CategoryCommand;
import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        Set<IngredientCommand> ingredientCommands = source.getIngredients().stream()
                .map(ingredientToIngredientCommand::convert)
                .collect(Collectors.toSet());

        Set<CategoryCommand> categoryCommands = source.getCategories().stream()
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());

        return RecipeCommand.builder()
                .id(source.getId()).description(source.getDescription())
                .prepTime(source.getPrepTime()).cookTime(source.getCookTime())
                .servings(source.getServings()).source(source.getSource())
                .url(source.getUrl()).directions(source.getDirections())
                .difficulty(source.getDifficulty())
                .notes(notesToNotesCommand.convert(source.getNotes()))
                .ingredients(ingredientCommands)
                .categories(categoryCommands)
                .build();
    }
}
