package de.kappa.paperchase.eventlisteners;

import de.kappa.paperchase.libraries.ItemLibrary;
import org.bukkit.ChatColor;
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

            Integer paperChaseId;
            if ((paperChaseId = ItemLibrary.getPaperChaseId(event.getItem().getItemStack())) != null) {
                if (event.getEntityType() == EntityType.PLAYER) {
                    Player p = (Player) event.getEntity();
                    p.sendMessage(ChatColor.RED + "[PaperMC] Found cookie #" + paperChaseId.toString());
                }

                event.setCancelled(true);
            }
        }
    }
}
