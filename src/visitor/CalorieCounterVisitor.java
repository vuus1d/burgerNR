package visitor;

public final class CalorieCounterVisitor implements OrderVisitor {
    private int totalCalories;

    @Override
    public void visitBurger(Burger burger) {
        addCalories(burger.getCalories());
    }

    @Override
    public void visitDrink(Drink drink) {
        addCalories(drink.getCalories());
    }

    @Override
    public void visitSide(Side side) {
        addCalories(side.getCalories());
    }

    @Override
    public void visitCombo(Combo combo) {
        combo.acceptItems(this);
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    private void addCalories(int calories) {
        totalCalories += calories;
    }
}
