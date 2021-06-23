package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.IngredientCommand;
import es.menasoft.recipe.commands.UnitOfMeasureCommand;
import es.menasoft.recipe.service.IngredientService;
import es.menasoft.recipe.service.RecipeService;
import es.menasoft.recipe.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private WebDataBinder webDataBinder;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Retrieving ingredients for recipe: {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(recipeId, id));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        model.addAttribute("ingredient", IngredientCommand.builder().recipeId(recipeId)
                .unitOfMeasure(UnitOfMeasureCommand.builder().build()).build());
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String id) {
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, id);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeId, @ModelAttribute IngredientCommand command, Model model) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));
            if (command.getId() == null || command.getId().isEmpty()) {
                return newRecipeIngredient(recipeId, model);
            } else {
                return updateRecipeIngredient(recipeId, command.getId(), model);
            }
        }

        command.setRecipeId(recipeId);
        IngredientCommand ingredient = ingredientService.saveIngredientCommand(command).block();

        log.debug("Saved recipe id: {}", ingredient.getRecipeId());
        log.debug("Saved ingredient id: {}", ingredient.getId());

        return "redirect:/recipe/" + recipeId + "/ingredient/" + ingredient.getId() + "/show";

    }
}
