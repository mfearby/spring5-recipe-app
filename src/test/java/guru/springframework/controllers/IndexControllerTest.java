package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.recipes.RecipeService;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.ui.Model;

public class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Model testModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);
    }

    @Test
    public void getIndexPage() {
        Category mexican = new Category();
        mexican.setDescription("Test Mexican");
        Optional<Category> optionalMexican = Optional.of(mexican);
        when(categoryRepository.findByDescription(any())).thenReturn(optionalMexican);

        UnitOfMeasure inch = new UnitOfMeasure();
        inch.setDescription("Test Inch");
        Optional<UnitOfMeasure> optionalInch = Optional.of(inch);
        when(unitOfMeasureRepository.findByDescription(any())).thenReturn(optionalInch);

        Recipe cake = new Recipe();
        cake.setDescription("Test Cake");
        Notes cakeNotes = new Notes();
        cakeNotes.setRecipeNotes("Test cake notes");
        cake.setNotes(cakeNotes);

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(cake);

        when(recipeService.getRecipes()).thenReturn(recipes);

        // Execute
        String result = indexController.getIndexPage(testModel);

        assertEquals("index", result);
        verify(recipeService, times(1)).getRecipes();
        verify(testModel, times(1)).addAttribute(eq("recipes"), anySet());
    }
}