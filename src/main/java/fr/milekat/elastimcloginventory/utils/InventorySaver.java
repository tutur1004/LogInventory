package fr.milekat.elastimcloginventory.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.milekat.elastimclogapi.ESMcLog;
import fr.milekat.elastimcloginventory.classes.InventoryLog;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Collections;
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

    /**
     *
     */
    public static void loadInventory() throws IOException, InvalidConfigurationException {
        InventoryLog inv = (InventoryLog) ESMcLog.getApi().getObject(InventoryLog.class,
                Collections.singletonMap("uuid.keyword", "uuid-test"),
                Collections.singletonMap("logDate", true));
        Inventory inventory = Bukkit.createInventory(null, inv.getInventory().type());
        inv.getInventory().slots().forEach(slot -> {
            try {
                YamlConfiguration yamlSlot = new YamlConfiguration();
                yamlSlot.loadFromString(new Yaml().dump(slot));
                ItemStack stack = yamlSlot.getItemStack("item");
                if (stack!=null) inventory.setItem(yamlSlot.getInt("slot"), stack);
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getOnlinePlayers().forEach(player -> player.openInventory(inventory));
    }
}
