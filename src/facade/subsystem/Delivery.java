package facade.subsystem;

import java.time.Instant;

/**
 * Subsystem class: a scheduled courier trip for one order.
 */
public record Delivery(String orderId, PackedOrder packedOrder, String address, Instant estimatedArrival) {
}
