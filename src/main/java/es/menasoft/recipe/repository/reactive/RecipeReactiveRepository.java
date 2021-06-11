package es.menasoft.recipe.repository.reactive;

import es.menasoft.recipe.domain.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, ObjectId> {
}
