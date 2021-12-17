package fr.milekat.elastimcloginventory;

import fr.milekat.elastimcloginventory.listeners.ConnectionInventoryLog;
import fr.milekat.elastimcloginventory.utils.InventorySaver;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new ConnectionInventoryLog(), this);
        this.getServer().getOnlinePlayers().forEach(player -> {
            try {
                InventorySaver.saveInventory(player.getUniqueId(),
                        player.getName(), player.getInventory());
                InventorySaver.loadInventory();
            } catch (InvalidConfigurationException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}
