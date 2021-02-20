package es.menasoft.recipe.controllers;

import es.menasoft.recipe.commands.RecipeCommand;
import es.menasoft.recipe.service.ImageService;
import es.menasoft.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    @GetMapping("/recipe/{recipeId}/image")
    public String loadImageForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.parseLong(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    @SneakyThrows
    public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) {

        RecipeCommand command = recipeService.findCommandById(Long.parseLong(recipeId));

        if (command.getImage() == null) {
            return;
        }

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(new ByteArrayInputStream(ArrayUtils.toPrimitive(command.getImage())), response.getOutputStream());
    }
}
