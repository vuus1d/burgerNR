package facade.subsystem;

import facade.model.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryDispatcherTest {
    private static final Instant NOW = Instant.parse("2026-10-18T12:00:00Z");
    private static final PackedOrder BAG = new PackedOrder(PackedOrder.Packaging.PAPER_BAG, 1);
    private static final String ZONE_A_ADDRESS = "Zone A, Mangilik El 55";

    private MutableClock clock;
    private DeliveryDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        clock = new MutableClock(NOW);
        dispatcher = new DeliveryDispatcher(clock);
    }

    @Test
    void schedule_zoneA_arrivesTwentyMinutesFromNow() {
        Delivery delivery = dispatcher.schedule("ORD-1", BAG, ZONE_A_ADDRESS);

        assertEquals(NOW.plus(Duration.ofMinutes(20)), delivery.estimatedArrival());
    }

    @Test
    void schedule_zoneB_arrivesThirtyFiveMinutesFromNow() {
        Delivery delivery = dispatcher.schedule("ORD-1", BAG, "Zone B, Turan Ave 37");

        assertEquals(NOW.plus(Duration.ofMinutes(35)), delivery.estimatedArrival());
    }

    @Test
    void schedule_unservedZone_throwsDeliveryExceptionNamingZone() {
        DeliveryException failure = assertThrows(DeliveryException.class,
                () -> dispatcher.schedule("ORD-1", BAG, "Zone Z, Nowhere 1"));

        assertEquals("Cannot schedule delivery for order ORD-1: zone 'Zone Z' is not served (served zones: Zone A, Zone B)",
                failure.getMessage());
    }

    @Test
    void schedule_blankAddress_throwsDeliveryException() {
        assertThrows(DeliveryException.class, () -> dispatcher.schedule("ORD-1", BAG, "  "));
    }

    @Test
    void schedule_sameOrderTwice_throwsDeliveryException() {
        dispatcher.schedule("ORD-1", BAG, ZONE_A_ADDRESS);

        assertThrows(DeliveryException.class, () -> dispatcher.schedule("ORD-1", BAG, ZONE_A_ADDRESS));
    }

    @Test
    void statusOf_unknownOrder_isUnknown() {
        assertEquals(DeliveryStatus.UNKNOWN, dispatcher.statusOf("ORD-404"));
    }

    @Test
    void statusOf_beforeEstimatedArrival_isOnTheWay() {
        dispatcher.schedule("ORD-1", BAG, ZONE_A_ADDRESS);
        clock.advance(Duration.ofMinutes(19));

        assertEquals(DeliveryStatus.ON_THE_WAY, dispatcher.statusOf("ORD-1"));
    }

    @Test
    void statusOf_atEstimatedArrival_isDelivered() {
        dispatcher.schedule("ORD-1", BAG, ZONE_A_ADDRESS);
        clock.advance(Duration.ofMinutes(20));

        assertEquals(DeliveryStatus.DELIVERED, dispatcher.statusOf("ORD-1"));
    }
}
