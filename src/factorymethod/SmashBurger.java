package factorymethod;

import burgerbuilder.Burger;

import java.util.List;

public class SmashBurger extends Burger implements BurgerProduct {

    public SmashBurger() {
        super(
                "Brioche",
                "Double Beef",
                "Pepper Jack",
                "Spicy Mayo",
                List.of("Pickles", "Jalapeno"),
                List.of("Crispy Bacon")
        );
    }

    @Override
    public void prepare() {
        System.out.println("Preparing Smash Burger");
    }
}