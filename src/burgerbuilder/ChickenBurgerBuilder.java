package burgerbuilder;

public class ChickenBurgerBuilder extends AbstractBurgerBuilder {

    @Override
    public Burger build() {
        if (bun == null) {
            setBun("Sesame Bun");
        }

        if (patty == null) {
            setPatty("Grilled Chicken");
        }

        if (cheese == null) {
            setCheese("Swiss");
        }

        if (sauce == null) {
            setSauce("Garlic Mayo");
        }

        if (vegetables.isEmpty()) {
            addVegetable("Lettuce");
            addVegetable("Tomato");
        }

        if (extras.isEmpty()) {
            addExtra("Crispy Onions");
        }

        return finishBurger();
    }
}