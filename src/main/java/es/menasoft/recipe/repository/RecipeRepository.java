package es.menasoft.recipe.repository;

import es.menasoft.recipe.domain.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RecipeRepository extends MongoRepository<Recipe, ObjectId> {
}
