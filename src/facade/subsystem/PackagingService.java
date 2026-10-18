package facade.subsystem;

import java.util.List;
import java.util.Objects;

/**
 * Subsystem class: packs quality-checked burgers into a paper bag or, for larger orders, a box.
 */
public class PackagingService {
    public static final int PAPER_BAG_CAPACITY = 2;

    public PackedOrder pack(List<PreparedBurger> burgers) {
        Objects.requireNonNull(burgers, "Burgers must not be null");
        if (burgers.isEmpty()) {
            throw new IllegalArgumentException("Cannot pack an order without burgers");
        }
        for (PreparedBurger burger : burgers) {
            if (!burger.passedQualityCheck()) {
                throw new IllegalArgumentException("Cannot pack " + burger.burgerType() + ": it has not passed the quality check");
            }
        }
        PackedOrder.Packaging packaging = burgers.size() <= PAPER_BAG_CAPACITY
                ? PackedOrder.Packaging.PAPER_BAG
                : PackedOrder.Packaging.BOX;
        return new PackedOrder(packaging, burgers.size());
    }
}
