package visitor;

public final class ReceiptPrinterVisitor implements OrderVisitor {
    private static final String INDENT = "    ";
    private static final String PRICED_LINE_FORMAT = "%-40s%7d tenge";
    private static final String COMBO_DISCOUNT_LABEL = "Combo discount";

    private final StringBuilder receipt = new StringBuilder();
    private int comboDepth;

    @Override
    public void visitBurger(Burger burger) {
        appendPricedLine(burger.getName(), burger.getPriceInTenge());
    }

    @Override
    public void visitDrink(Drink drink) {
        appendPricedLine(drink.getName() + " " + drink.getVolumeInMl() + " ml", drink.getPriceInTenge());
    }

    @Override
    public void visitSide(Side side) {
        appendPricedLine(side.getName(), side.getPriceInTenge());
    }

    @Override
    public void visitCombo(Combo combo) {
        appendLine(currentIndent() + combo.getName() + " (" + combo.getItems().size() + " items)");
        comboDepth++;
        combo.acceptItems(this);
        appendPricedLine(COMBO_DISCOUNT_LABEL, -combo.getDiscountInTenge());
        comboDepth--;
    }

    public String getReceipt() {
        return receipt.toString();
    }

    private void appendPricedLine(String label, int priceInTenge) {
        appendLine(String.format(PRICED_LINE_FORMAT, currentIndent() + label, priceInTenge));
    }

    private void appendLine(String line) {
        receipt.append(line).append(System.lineSeparator());
    }

    private String currentIndent() {
        return INDENT.repeat(comboDepth);
    }
}
