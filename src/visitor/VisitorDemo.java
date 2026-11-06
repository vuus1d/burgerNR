package visitor;

import java.util.List;

public final class VisitorDemo {
    private static final String SEPARATOR = "-".repeat(53);

    public static void main(String[] args) {
        Order order = createSampleOrder();

        System.out.println("=== VISITOR PATTERN DEMO ===");
        System.out.println("Order with " + order.getItems().size() + " top-level items");
        printReceipt(order);
        printTotalPrice(order);
        printTotalCalories(order);
    }

    private static Order createSampleOrder() {
        Combo kidsCombo = new Combo("Kids Combo", 300, List.of(
                new Burger("Mini Cheeseburger", 1290, 420),
                new Side("Apple Slices", 490, 60),
                new Drink("Orange Juice", 250, 590, 120)
        ));

        Combo familyCombo = new Combo("Family Combo", 900, List.of(
                new Burger("Chicken Burger", 2190, 650),
                new Side("French Fries", 890, 380),
                new Drink("Lemonade", 400, 790, 180),
                kidsCombo
        ));

        return new Order(List.of(
                new Burger("Classic Smash Burger", 2490, 780),
                new Side("Onion Rings", 990, 410),
                new Drink("Cola", 500, 690, 210),
                familyCombo
        ));
    }

    private static void printReceipt(Order order) {
        ReceiptPrinterVisitor receiptPrinter = new ReceiptPrinterVisitor();
        order.accept(receiptPrinter);
        printSection("Receipt (ReceiptPrinterVisitor)");
        System.out.print(receiptPrinter.getReceipt());
    }

    private static void printTotalPrice(Order order) {
        PriceCalculatorVisitor priceCalculator = new PriceCalculatorVisitor();
        order.accept(priceCalculator);
        printSection("Total price (PriceCalculatorVisitor)");
        System.out.println(priceCalculator.getTotalPriceInTenge() + " tenge");
    }

    private static void printTotalCalories(Order order) {
        CalorieCounterVisitor calorieCounter = new CalorieCounterVisitor();
        order.accept(calorieCounter);
        printSection("Total calories (CalorieCounterVisitor)");
        System.out.println(calorieCounter.getTotalCalories() + " kcal");
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println(title);
        System.out.println(SEPARATOR);
    }
}
