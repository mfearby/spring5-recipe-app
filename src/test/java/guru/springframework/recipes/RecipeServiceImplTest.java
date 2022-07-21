package guru.springframework.recipes;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        // Mockito 2.x (deprecated). Recommended way is to use this annotation on the test class:
        // @ExtendWith(MockitoExtension.class)   or, in Kotlin:   @ExtendWith(MockitoExtension::class)
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() {
        HashSet recipes = new HashSet();
        recipes.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> actualRecipes = recipeService.getRecipes();
        assertEquals(1, actualRecipes.size());
        verify(recipeRepository, times(1)).findAll();
    }
}