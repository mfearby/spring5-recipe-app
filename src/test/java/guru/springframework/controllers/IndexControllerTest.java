package guru.springframework.controllers;

import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.recipes.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

public class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model testModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        // GIVEN
        Recipe cake = new Recipe();
        cake.setDescription("Test Cake");
        Notes cakeNotes = new Notes();
        cakeNotes.setRecipeNotes("Test cake notes");
        cake.setNotes(cakeNotes);

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(cake);

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> capturedRecipes = ArgumentCaptor.forClass(Set.class);

        // WHEN
        String result = indexController.getIndexPage(testModel);

        // THEN
        assertEquals("index", result);
        verify(recipeService, times(1)).getRecipes();
        verify(testModel, times(1)).addAttribute(eq("recipes"), capturedRecipes.capture());

        Set<Recipe> actualRecipes = capturedRecipes.getValue();
        assertEquals(1, actualRecipes.size());

        Recipe firstRecipe = (Recipe)actualRecipes.toArray()[0];
        assertEquals("Test Cake", firstRecipe.getDescription());
    }
}