package fr.milekat.elastimcloginventory.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.milekat.elastimclogapi.ESMcLog;
import fr.milekat.elastimcloginventory.classes.InventoryLog;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class InventorySaver {
    /**
     * Save all inventory of a given entity
     */
    public static void saveInventory(UUID uuid, String entityName, Inventory inventory) throws JsonProcessingException {
        List<Object> slots = new LinkedList<>();
        IntStream.range(0, inventory.getSize()).forEach(slot -> {
            try {
                ItemStack item = inventory.getItem(slot);
                if (item==null || item.getType().isAir()) return;
                YamlConfiguration yaml = new YamlConfiguration();
                yaml.set("slot", slot);
                yaml.set("item", item);
                slots.add(new ObjectMapper(new YAMLFactory()).readValue(yaml.saveToString(), Object.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        ESMcLog.getApi().addPendingBulk(new InventoryLog(uuid, entityName,
                new InventoryLog.Inventory(inventory.getType(), slots)));

    }
}
