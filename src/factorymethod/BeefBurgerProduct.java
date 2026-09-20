package factorymethod;

public class BeefBurgerProduct implements BurgerProduct {

    @Override
    public void prepare() {
        System.out.println("Preparing Beef Burger");
    }
}