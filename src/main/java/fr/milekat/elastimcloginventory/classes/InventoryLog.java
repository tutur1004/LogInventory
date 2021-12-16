package fr.milekat.elastimcloginventory.classes;

import fr.milekat.utils.DateMileKat;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;
import java.util.UUID;

public class InventoryLog {
    private final UUID uuid;
    private final String entityName;
    private final Inventory inventory;
    private final String logDate;

    public InventoryLog(UUID uuid, String entityName, Inventory inventory) {
        this.uuid = uuid;
        this.entityName = entityName;
        this.inventory = inventory;
        this.logDate = DateMileKat.getDateEs();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getEntityName() {
        return entityName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getLogDate() {
        return logDate;
    }

    public record Inventory(InventoryType type, List<Object> slots) {}
}
