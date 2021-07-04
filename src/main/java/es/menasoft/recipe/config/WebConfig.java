package es.menasoft.recipe.config;

import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WebConfig {

    @Bean
    RouterFunction<?> routers(RecipeService recipeService) {
        return RouterFunctions.route(RequestPredicates.GET("/api/recipes"),
                serverRequest -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(recipeService.findAll(), Recipe.class));
    }
}
