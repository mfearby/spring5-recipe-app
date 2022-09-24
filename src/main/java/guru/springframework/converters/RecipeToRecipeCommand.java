package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand cmd = new RecipeCommand();
        cmd.setId(source.getId());
        cmd.setCookTime(source.getCookTime());
        cmd.setPrepTime(source.getPrepTime());
        cmd.setDescription(source.getDescription());
        cmd.setDifficulty(source.getDifficulty());
        cmd.setDirections(source.getDirections());
        cmd.setServings(source.getServings());
        cmd.setSource(source.getSource());
        cmd.setUrl(source.getUrl());
        cmd.setNotes(notesConverter.convert(source.getNotes()));
        cmd.setImage(source.getImage());

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach((Category category) -> cmd.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> cmd.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return cmd;
    }
}