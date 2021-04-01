package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.exception.NotFoundException;
import es.menasoft.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    @SuppressWarnings("SpringMVCViewInspection")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand result = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + result.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    @SuppressWarnings("SpringMVCViewInspection")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(Long.parseLong(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound() {
        log.error("Handling not found exception");
        return new ModelAndView("404error");
    }

}
