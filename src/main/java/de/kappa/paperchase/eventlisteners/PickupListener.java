package de.kappa.paperchase.eventlisteners;

import de.kappa.paperchase.libraries.ItemLibrary;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupListener implements Listener {
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        // Cancel pickup
        Material material = event.getItem().getItemStack().getType();

        if (material == Material.COOKIE) {
            if (ItemLibrary.hasPaperChaseId(event.getItem().getItemStack())) {
                if (event.getEntityType() == EntityType.PLAYER) {
                    Player p = (Player) event.getEntity();
                    p.sendMessage("[PaperMC] Its a bird? Its a plane? Its a cookie!");
                }

                event.setCancelled(true);
            }
        }
    }
}
