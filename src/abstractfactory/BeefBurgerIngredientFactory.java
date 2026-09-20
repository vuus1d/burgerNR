package abstractfactory;

public class BeefBurgerIngredientFactory implements BurgerIngredientFactory {

    @Override
    public Bun createBun() {
        return new BriocheBun();
    }

    @Override
    public Sauce createSauce() {
        return new BBQSauce();
    }
}