package factorymethod;

public class FactoryMethodDemo {

    public static void main(String[] args) {

        BurgerFactory smashFactory = new SmashBurgerFactory();
        smashFactory.orderBurger();

        System.out.println();

        BurgerFactory chickenFactory = new ChickenBurgerFactory();
        chickenFactory.orderBurger();
    }
}