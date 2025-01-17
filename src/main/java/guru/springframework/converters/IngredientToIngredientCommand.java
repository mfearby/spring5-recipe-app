package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            cmd.setRecipeId(ingredient.getRecipe().getId());
        }
        cmd.setAmount(ingredient.getAmount());
        cmd.setDescription(ingredient.getDescription());
        cmd.setUom(uomConverter.convert(ingredient.getUom()));
        return cmd;
    }

}