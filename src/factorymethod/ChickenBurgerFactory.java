package factorymethod;

public class ChickenBurgerFactory extends BurgerFactory {

    @Override
    public BurgerProduct createBurger() {
        return new ChickenBurger();
    }
}