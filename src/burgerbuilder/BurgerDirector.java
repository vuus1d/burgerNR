package burgerbuilder;

public class BurgerDirector {

    public Burger buildSmashBurger() {
        BurgerBuilder builder = new SmashBurgerBuilder();

        return builder
                .setBun("Brioche")
                .setPatty("Double Beef")
                .setCheese("Cheddar")
                .setSauce("Smoky BBQ")
                .addVegetable("Pickles")
                .addVegetable("Red Onion")
                .addExtra("Crispy Bacon")
                .build();
    }

    public Burger buildChickenBurger() {
        BurgerBuilder builder = new ChickenBurgerBuilder();

        return builder
                .setBun("Sesame Bun")
                .setPatty("Grilled Chicken")
                .setCheese("Swiss")
                .setSauce("Garlic Mayo")
                .addVegetable("Lettuce")
                .addVegetable("Tomato")
                .addExtra("Crispy Onions")
                .build();
    }
}