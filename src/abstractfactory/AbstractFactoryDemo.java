package abstractfactory;

public class AbstractFactoryDemo {

    public static void main(String[] args) {

        BurgerApplication beefBurger =
                new BurgerApplication(new BeefBurgerIngredientFactory());

        System.out.println("Beef Burger:");
        beefBurger.prepareBurger();

        System.out.println();

        BurgerApplication chickenBurger =
                new BurgerApplication(new ChickenBurgerIngredientFactory());

        System.out.println("Chicken Burger:");
        chickenBurger.prepareBurger();
    }
}