package facade;

import facade.model.BurgerType;
import facade.subsystem.InventoryService;
import facade.subsystem.Reservation;

import java.util.List;

/**
 * Hand-written spy: the real inventory, plus a journal entry for every reserve and release.
 */
final class JournalingInventoryService extends InventoryService {
    private final List<String> journal;

    JournalingInventoryService(List<String> journal) {
        this.journal = journal;
    }

    @Override
    public Reservation reserve(BurgerType burgerType, int quantity) {
        Reservation reservation = super.reserve(burgerType, quantity);
        journal.add("reserve");
        return reservation;
    }

    @Override
    public void release(Reservation reservation) {
        super.release(reservation);
        journal.add("release");
    }
}
