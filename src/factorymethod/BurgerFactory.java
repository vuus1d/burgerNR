package factorymethod;

public abstract class BurgerFactory {

    public abstract BurgerProduct createBurger();

    public void orderBurger() {
        BurgerProduct burger = createBurger();
        burger.prepare();
    }
}