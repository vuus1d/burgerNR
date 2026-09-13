import burgerbuilder.Burger;
import burgerbuilder.BurgerDirector;
import burgerbuilder.SmashBurgerBuilder;

public class Main {

    public static void main(String[] args) {

        Burger customBurger = new SmashBurgerBuilder()
                .setBun("Brioche")
                .setPatty("Double Beef")
                .setCheese("Pepper Jack")
                .setSauce("Spicy Mayo")
                .addVegetable("Pickles")
                .addVegetable("Jalapeno")
                .addExtra("Crispy Bacon")
                .build();

        BurgerDirector director = new BurgerDirector();
        Burger chickenBurger = director.buildChickenBurger();

        System.out.println("=== BURGER BUILDER DEMO ===");
        System.out.println();
        System.out.println("Custom Smash Burger:");
        System.out.println(customBurger);

        System.out.println();
        System.out.println("Director's Chicken Burger:");
        System.out.println(chickenBurger);
    }
}