package net.guildcraft.survivallevels.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GUIListeners implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = GUITemplate.openInventories.get(playerUUID);
        if(GUITemplate.getOpenInventories().containsKey(playerUUID)) {
            GUITemplate gui = GUITemplate.getInventoriesByUUID().get(inventoryUUID);
            GUITemplate.GUIAction action = gui.getActions().get(e.getSlot());

            if(GUITemplate.openInventories.containsKey(playerUUID)) {
                e.setCancelled(true);
                if(e.getClickedInventory() != player.getOpenInventory().getTopInventory()) return;
                if (action != null) {
                    action.click(player);
                }
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(GUITemplate.getOpenInventories().containsKey(playerUUID)) {
            GUITemplate.getOpenInventories().remove(playerUUID);
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if(GUITemplate.getOpenInventories().containsKey(playerUUID)) {
            GUITemplate.getOpenInventories().remove(playerUUID);
        }
    }
}
