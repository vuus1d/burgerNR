package visitor;

import java.util.List;

public final class Order {
    private final List<MenuItem> items;

    public Order(List<MenuItem> items) {
        this.items = List.copyOf(items);
    }

    public void accept(OrderVisitor visitor) {
        items.forEach(item -> item.accept(visitor));
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
