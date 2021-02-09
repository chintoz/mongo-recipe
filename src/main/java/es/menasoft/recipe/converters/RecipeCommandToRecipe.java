package es.menasoft.recipe.converters;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.domain.Category;
import es.menasoft.recipe.domain.Ingredient;
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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;
    private final CategoryCommandToCategory categoryCommandToCategory;

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        Set<Ingredient> ingredients = source.getIngredients().stream()
                .map(ingredientCommandToIngredient::convert)
                .collect(Collectors.toSet());

        Set<Category> categories = source.getCategories().stream()
                .map(categoryCommandToCategory::convert)
                .collect(Collectors.toSet());

        return Recipe.builder()
                .id(source.getId()).description(source.getDescription())
                .prepTime(source.getPrepTime()).cookTime(source.getCookTime())
                .servings(source.getServings()).source(source.getSource())
                .url(source.getUrl()).directions(source.getDirections())
                .difficulty(source.getDifficulty())
                .notes(notesCommandToNotes.convert(source.getNotes()))
                .ingredients(ingredients)
                .categories(categories)
                .build();
    }
}
