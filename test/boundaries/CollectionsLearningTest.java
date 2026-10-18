package boundaries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Learning test: the Map and List behaviour that InventoryService, PreparedBurger and KitchenStation rely on.
 */
class CollectionsLearningTest {
    private enum Item { BUN, PATTY }

    @Test
    void merge_absentKey_storesTheGivenValue() {
        Map<Item, Integer> stock = new EnumMap<>(Item.class);

        stock.merge(Item.BUN, 5, Integer::sum);

        assertEquals(5, stock.get(Item.BUN));
    }

    @Test
    void merge_presentKey_combinesOldAndNewValue() {
        Map<Item, Integer> stock = new EnumMap<>(Map.of(Item.BUN, 5));

        stock.merge(Item.BUN, -2, Integer::sum);

        assertEquals(3, stock.get(Item.BUN));
    }

    @Test
    void getOrDefault_absentKey_returnsTheDefault() {
        Map<Item, Integer> stock = new EnumMap<>(Item.class);

        assertEquals(0, stock.getOrDefault(Item.PATTY, 0));
    }

    @Test
    void getOrDefault_absentKey_doesNotInsertIt() {
        Map<Item, Integer> stock = new EnumMap<>(Item.class);

        stock.getOrDefault(Item.PATTY, 0);

        assertFalse(stock.containsKey(Item.PATTY));
    }

    @Test
    void listCopyOf_isNotAffectedByLaterChangesToTheSource() {
        List<String> source = new ArrayList<>(List.of("bun"));
        List<String> copy = List.copyOf(source);

        source.add("patty");

        assertEquals(List.of("bun"), copy);
    }

    @Test
    void listCopyOf_cannotBeModified() {
        List<String> copy = List.copyOf(List.of("bun"));

        assertThrows(UnsupportedOperationException.class, () -> copy.add("patty"));
    }

    @Test
    void unmodifiableList_isOnlyAViewThatShowsLaterChangesToTheSource() {
        List<String> source = new ArrayList<>(List.of("bun"));
        List<String> view = Collections.unmodifiableList(source);

        source.add("patty");

        assertEquals(List.of("bun", "patty"), view);
    }
}
