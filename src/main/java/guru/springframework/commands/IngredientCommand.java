package guru.springframework.commands;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private BigDecimal amount;
    private String description;
    private UnitOfMeasureCommand uom;
}
