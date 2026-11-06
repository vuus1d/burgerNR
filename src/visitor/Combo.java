package visitor;

import java.util.List;

public final class Combo implements MenuItem {
    private final String name;
    private final int discountInTenge;
    private final List<MenuItem> items;

    public Combo(String name, int discountInTenge, List<MenuItem> items) {
        this.name = name;
        this.discountInTenge = discountInTenge;
        this.items = List.copyOf(items);
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visitCombo(this);
    }

    public void acceptItems(OrderVisitor visitor) {
        items.forEach(item -> item.accept(visitor));
    }

    public String getName() {
        return name;
    }

    public int getDiscountInTenge() {
        return discountInTenge;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
