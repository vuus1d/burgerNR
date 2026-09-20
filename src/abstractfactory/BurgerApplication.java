package abstractfactory;

public class BurgerApplication {

    private final BurgerIngredientFactory factory;

    public BurgerApplication(BurgerIngredientFactory factory) {
        this.factory = factory;
    }

    public void prepareBurger() {
        Bun bun = factory.createBun();
        Sauce sauce = factory.createSauce();

        bun.prepare();
        sauce.add();
    }
}