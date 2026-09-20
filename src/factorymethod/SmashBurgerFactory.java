package factorymethod;

public class SmashBurgerFactory extends BurgerFactory {

    @Override
    public BurgerProduct createBurger() {
        return new SmashBurger();
    }
}