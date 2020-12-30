package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.converter = converter;
    }

    @Override
    @Synchronized
    @Nullable
    public IngredientCommand convert(Ingredient source) {
        if(source == null) {
            return null;
        }
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(source.getId());
        ingredientCommand.setAmount(source.getAmount());
        if(source.getRecipe() != null){
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setUom(converter.convert(source.getUom()));
        return ingredientCommand;
    }
}
