import burgerbuilder.Burger;
import burgerbuilder.BurgerDirector;

public class Main {

    public static void main(String[] args) {
        BurgerDirector director = new BurgerDirector();

        Burger smashBurger = director.buildSmashBurger();
        Burger chickenBurger = director.buildChickenBurger();

        System.out.println("=== BURGER BUILDER DEMO ===");
        System.out.println();
        System.out.println("Smash Burger:");
        System.out.println(smashBurger);

        System.out.println();
        System.out.println("Chicken Burger:");
        System.out.println(chickenBurger);
    }
}