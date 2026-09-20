package abstractfactory;

public class ChickenBurgerIngredientFactory implements BurgerIngredientFactory {

    @Override
    public Bun createBun() {
        return new SesameBun();
    }

    @Override
    public Sauce createSauce() {
        return new GarlicSauce();
    }
}