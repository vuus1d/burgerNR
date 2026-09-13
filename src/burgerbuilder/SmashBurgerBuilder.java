package burgerbuilder;

public class SmashBurgerBuilder extends AbstractBurgerBuilder {

    @Override
    public Burger build() {
        if (bun == null) {
            setBun("Brioche");
        }

        if (patty == null) {
            setPatty("Double Beef");
        }

        if (cheese == null) {
            setCheese("Cheddar");
        }

        if (sauce == null) {
            setSauce("Smoky BBQ");
        }

        if (vegetables.isEmpty()) {
            addVegetable("Pickles");
            addVegetable("Red Onion");
        }

        if (extras.isEmpty()) {
            addExtra("Crispy Bacon");
        }

        return finishBurger();
    }
}