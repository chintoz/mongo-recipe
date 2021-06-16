package es.menasoft.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    private ObjectId id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    @Builder.Default
    private Set<Ingredient> ingredients = new HashSet<>();
    private byte[] image;
    private Notes notes;

    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Recipe addingIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

}
