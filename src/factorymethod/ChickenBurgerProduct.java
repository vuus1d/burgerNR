package factorymethod;

public class ChickenBurgerProduct implements BurgerProduct {

    @Override
    public void prepare() {
        System.out.println("Preparing Chicken Burger");
    }
}