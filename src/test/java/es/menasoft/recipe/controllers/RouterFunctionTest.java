package es.menasoft.recipe.controllers;

import es.menasoft.recipe.config.WebConfig;
import es.menasoft.recipe.domain.Recipe;
import es.menasoft.recipe.service.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

public class RouterFunctionTest {

    WebTestClient webTestClient;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    @SneakyThrows
    public void setup() {
        MockitoAnnotations.openMocks(this);

        WebConfig webConfig = new WebConfig();

        RouterFunction<?> routers = webConfig.routers(recipeService);

        webTestClient = WebTestClient.bindToRouterFunction(routers).build();

    }

    @Test
    void generateGetRecipes() {

        when(recipeService.findAll()).thenReturn(Flux.just(Recipe.builder().build()));

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }

    @Test
    void generateGetEmptyRecipes() {

        when(recipeService.findAll()).thenReturn(Flux.empty());

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}
