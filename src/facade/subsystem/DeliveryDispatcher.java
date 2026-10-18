package facade.subsystem;

import facade.model.DeliveryStatus;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Subsystem class: schedules couriers to the served zones and reports where each order is.
 */
public class DeliveryDispatcher {
    private static final Map<String, Duration> TRAVEL_TIME_BY_ZONE = Map.of(
            "Zone A", Duration.ofMinutes(20),
            "Zone B", Duration.ofMinutes(35));

    private final Clock clock;
    private final Map<String, Delivery> deliveriesByOrderId = new HashMap<>();

    public DeliveryDispatcher(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "Clock must not be null");
    }

    /**
     * The address starts with its zone, e.g. "Zone A, Mangilik El 55".
     */
    public Delivery schedule(String orderId, PackedOrder packedOrder, String address) {
        Objects.requireNonNull(orderId, "Order id must not be null");
        Objects.requireNonNull(packedOrder, "Packed order must not be null");
        Objects.requireNonNull(address, "Address must not be null");
        if (address.isBlank()) {
            throw new DeliveryException("Cannot schedule delivery for order " + orderId + ": address is blank");
        }
        if (deliveriesByOrderId.containsKey(orderId)) {
            throw new DeliveryException("Cannot schedule delivery for order " + orderId + ": it is already scheduled");
        }
        Instant estimatedArrival = clock.instant().plus(travelTimeTo(orderId, address));
        Delivery delivery = new Delivery(orderId, packedOrder, address, estimatedArrival);
        deliveriesByOrderId.put(orderId, delivery);
        return delivery;
    }

    public DeliveryStatus statusOf(String orderId) {
        Objects.requireNonNull(orderId, "Order id must not be null");
        Delivery delivery = deliveriesByOrderId.get(orderId);
        if (delivery == null) {
            return DeliveryStatus.UNKNOWN;
        }
        return clock.instant().isBefore(delivery.estimatedArrival()) ? DeliveryStatus.ON_THE_WAY : DeliveryStatus.DELIVERED;
    }

    private static Duration travelTimeTo(String orderId, String address) {
        String zone = zoneOf(address);
        Duration travelTime = TRAVEL_TIME_BY_ZONE.get(zone);
        if (travelTime == null) {
            throw new DeliveryException(String.format(
                    "Cannot schedule delivery for order %s: zone '%s' is not served (served zones: %s)",
                    orderId, zone, String.join(", ", new TreeSet<>(TRAVEL_TIME_BY_ZONE.keySet()))));
        }
        return travelTime;
    }

    private static String zoneOf(String address) {
        int comma = address.indexOf(',');
        return (comma < 0 ? address : address.substring(0, comma)).trim();
    }
}
