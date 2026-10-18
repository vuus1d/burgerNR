package facade.subsystem;

/**
 * Subsystem class: prepared burgers packed and ready for a courier.
 */
public record PackedOrder(Packaging packaging, int burgerCount) {
    public enum Packaging {
        PAPER_BAG,
        BOX
    }
}
