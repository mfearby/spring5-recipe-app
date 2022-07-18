package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Loading recipes...");
        List<Recipe> recipes = getRecipes();
        recipeRepository.saveAll(recipes);
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        try {
            // Get units of measure
            UnitOfMeasure each = unitOfMeasureRepository.findByDescription("Each").get();
            UnitOfMeasure tblSpoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
            UnitOfMeasure teaSpoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
            UnitOfMeasure dash = unitOfMeasureRepository.findByDescription("Dash").get();
            UnitOfMeasure pint = unitOfMeasureRepository.findByDescription("Pint").get();
            UnitOfMeasure cup = unitOfMeasureRepository.findByDescription("Cup").get();
            UnitOfMeasure pinch = unitOfMeasureRepository.findByDescription("Pinch").get();
            UnitOfMeasure ounce = unitOfMeasureRepository.findByDescription("Ounce").get();

            // Get categories
            Category american = categoryRepository.findByDescription("American").get();
            Category mexican = categoryRepository.findByDescription("Mexican").get();

            Recipe guac = new Recipe();
            guac.setDescription("Perfect Guacamole");
            guac.setPrepTime(10);
            guac.setCookTime(0);
            guac.setDifficulty(Difficulty.EASY);
            guac.setDirections("1. Cut the avocado\n2. Mash the avocado flesh\n3. Add the remaining ingredients to taste\n4. Serve immediately");

            Notes notes = new Notes();
            notes.setRecipeNotes("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
            guac.setNotes(notes);

            guac.addIngredient(new Ingredient("Ripe avocados", BigDecimal.valueOf(2), each));
            guac.addIngredient(new Ingredient("Teaspoon of salt, plus more to taste", BigDecimal.valueOf(0.25), teaSpoon));
            guac.addIngredient(new Ingredient("Tablespoons minced red onion or thinly sliced green onion", BigDecimal.valueOf(2), tblSpoon));
            guac.addIngredient(new Ingredient("Serrano (or jalapeño) chilis, stems and seeds removed, minced", BigDecimal.valueOf(1), each));
            guac.addIngredient(new Ingredient("Cilantro (leaves and tender stems), finely chopped", BigDecimal.valueOf(2), tblSpoon));
            guac.addIngredient(new Ingredient("Freshly ground black pepper", BigDecimal.valueOf(1), pinch));
            guac.addIngredient(new Ingredient("Ripe tomato, chopped (optional)", BigDecimal.valueOf(0.5), each));
            guac.addIngredient(new Ingredient("Red radish or jicama slices for garnish (optional)", BigDecimal.valueOf(1), each));

            guac.getCategories().add(mexican);

            recipes.add(guac);

            // etc (similar for American recipe)

        } catch (Exception ex) {
            log.error("getRecipes() Exception was thrown:\n");
            ex.printStackTrace();
        }

        return recipes;
    }
}
