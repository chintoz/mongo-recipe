package es.menasoft.recipe.repository;

import es.menasoft.recipe.domain.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, ObjectId> {
}
