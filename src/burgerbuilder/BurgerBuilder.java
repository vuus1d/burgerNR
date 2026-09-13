package burgerbuilder;

public interface BurgerBuilder {

    BurgerBuilder setBun(String bun);

    BurgerBuilder setPatty(String patty);

    BurgerBuilder setCheese(String cheese);

    BurgerBuilder setSauce(String sauce);

    BurgerBuilder addVegetable(String vegetable);

    BurgerBuilder addExtra(String extra);

    Burger build();
}