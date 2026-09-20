package factorymethod;

import burgerbuilder.Burger;

import java.util.List;

public class ChickenBurger extends Burger implements BurgerProduct {

    public ChickenBurger() {
        super(
                "Sesame Bun",
                "Grilled Chicken",
                "Swiss",
                "Garlic Mayo",
                List.of("Lettuce", "Tomato"),
                List.of("Crispy Onions")
        );
    }

    @Override
    public void prepare() {
        System.out.println("Preparing Chicken Burger");
    }
}