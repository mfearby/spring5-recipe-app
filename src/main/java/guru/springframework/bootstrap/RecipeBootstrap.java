package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

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
            notes.setRecipe(guac);
            guac.setNotes(notes);

            Set<Ingredient> items = new HashSet<>();
            items.add(new Ingredient("Ripe avocados", BigDecimal.valueOf(2), guac, each));
            items.add(new Ingredient("Teaspoon of salt, plus more to taste", BigDecimal.valueOf(0.25), guac, teaSpoon));
            items.add(new Ingredient("Tablespoons minced red onion or thinly sliced green onion", BigDecimal.valueOf(2), guac, tblSpoon));
            items.add(new Ingredient("Serrano (or jalapeño) chilis, stems and seeds removed, minced", BigDecimal.valueOf(1), guac, each));
            items.add(new Ingredient("Cilantro (leaves and tender stems), finely chopped", BigDecimal.valueOf(2), guac, tblSpoon));
            items.add(new Ingredient("Freshly ground black pepper", BigDecimal.valueOf(1), guac, pinch));
            items.add(new Ingredient("Ripe tomato, chopped (optional)", BigDecimal.valueOf(0.5), guac, each));
            items.add(new Ingredient("Red radish or jicama slices for garnish (optional)", BigDecimal.valueOf(1), guac, each));
            guac.setIngredients(items);

            guac.getCategories().add(mexican);

            recipes.add(guac);

            // etc (similar for American recipe)

        } catch (Exception ex) {
            System.out.println("getRecipes() Exception was thrown:\n");
            ex.printStackTrace();
        }

        return recipes;
    }
}
