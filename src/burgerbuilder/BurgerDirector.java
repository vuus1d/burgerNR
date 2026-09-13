package burgerbuilder;

public class BurgerDirector {

    public Burger buildSmashBurger() {
        return new SmashBurgerBuilder().build();
    }

    public Burger buildChickenBurger() {
        return new ChickenBurgerBuilder().build();
    }
}