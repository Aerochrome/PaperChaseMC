package de.kappa.paperchase.eventlisteners;

import de.kappa.paperchase.libraries.ItemLibrary;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

public class DespawnListener implements Listener {
    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        if (event.getEntity().getItemStack().getType().equals(Material.COOKIE)) {
            if (ItemLibrary.hasPaperChaseId(event.getEntity().getItemStack())) {
                event.setCancelled(true);
            }
        }
    }
}
