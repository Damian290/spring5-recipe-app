package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReciveServiceImplTest {

    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new ReciveServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipeByIdTest(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById(1L);
        assertNotNull(returnedRecipe,"Not null recipe returned");
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    void getRecipesTest() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipse = recipeService.getRecipes();
        assertEquals(recipse.size(),1);
        //verify(recipeRepository,times(1));
        verify(recipeRepository,never()).findById(anyLong());
    }

    @Test
    public void testGetRecipeByIdNotFound(){
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Assertions.assertThrows(NotFoundException.class,() -> {
            recipeService.findById(1l);
        });
    }

    @Test
    public void testDeleteById(){
        Long idToDelete = Long.valueOf(2L);
        recipeService.deleteById(idToDelete);
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }
}