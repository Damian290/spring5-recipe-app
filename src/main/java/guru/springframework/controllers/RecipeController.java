package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@Slf4j
public class RecipeController {

    public static final String RECIPE_RECIPEFORM = "recipe/recipeform";
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        try {
            model.addAttribute("recipe", recipeService.findById(new Long(id)));
        } catch (NumberFormatException ex){
            throw new NumberFormatException("Cannot cast: " + id + " to Long");
        }
        return"recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return RECIPE_RECIPEFORM;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("Deleting " + id + " recipe");

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/recipe/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

//    @ExceptionHandler(NumberFormatException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView badRequest(Exception exception){
//        log.error("Bad request. Cannot cast String to long");
//        log.error(exception.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/recipe/400error");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }
}
