package abstractfactory;

public interface BurgerIngredientFactory {

    Bun createBun();

    Sauce createSauce();
}