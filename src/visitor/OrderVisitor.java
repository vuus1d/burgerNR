package visitor;

public interface OrderVisitor {
    void visitBurger(Burger burger);

    void visitDrink(Drink drink);

    void visitSide(Side side);

    void visitCombo(Combo combo);
}
