package visitor;

public final class PriceCalculatorVisitor implements OrderVisitor {
    private int totalPriceInTenge;

    @Override
    public void visitBurger(Burger burger) {
        addPrice(burger.getPriceInTenge());
    }

    @Override
    public void visitDrink(Drink drink) {
        addPrice(drink.getPriceInTenge());
    }

    @Override
    public void visitSide(Side side) {
        addPrice(side.getPriceInTenge());
    }

    @Override
    public void visitCombo(Combo combo) {
        combo.acceptItems(this);
        subtractDiscount(combo.getDiscountInTenge());
    }

    public int getTotalPriceInTenge() {
        return totalPriceInTenge;
    }

    private void addPrice(int priceInTenge) {
        totalPriceInTenge += priceInTenge;
    }

    private void subtractDiscount(int discountInTenge) {
        totalPriceInTenge -= discountInTenge;
    }
}
