package fr.milekat.elastimcloginventory;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.milekat.elastimcloginventory.listeners.ConnectionInventoryLog;
import fr.milekat.elastimcloginventory.utils.InventorySaver;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new ConnectionInventoryLog(), this);
        this.getServer().getOnlinePlayers().forEach(player -> {
            try {
                InventorySaver.saveInventory(player.getUniqueId(),
                        player.getName(), player.getInventory());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
