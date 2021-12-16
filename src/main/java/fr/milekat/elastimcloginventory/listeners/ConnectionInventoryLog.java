package fr.milekat.elastimcloginventory.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.milekat.elastimcloginventory.utils.InventorySaver;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record ConnectionInventoryLog() implements Listener {
    @EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onJoinLog(PlayerJoinEvent event) throws JsonProcessingException {
        log(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onQuitLog(PlayerQuitEvent event) throws JsonProcessingException {
        log(event.getPlayer());
    }

    private void log(Player player) throws JsonProcessingException {
        InventorySaver.saveInventory(player.getUniqueId(), player.getName(), player.getInventory());
    }
}
