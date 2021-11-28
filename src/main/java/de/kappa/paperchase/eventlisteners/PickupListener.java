package de.kappa.paperchase.eventlisteners;

import de.kappa.paperchase.libraries.ItemLibrary;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class PickupListener implements Listener {
    public HashMap<String, Long> pickupCooldownMap = new HashMap<String, Long>();

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            event.setCancelled(true);
            return;
        }

        ItemStack stack = event.getItem().getItemStack();
        Material material = stack.getType();

        if (material == Material.COOKIE) {

            // Cooldown check
            String itemUUIDString = event.getItem().getUniqueId().toString();

            Long cooldownUntil = this.pickupCooldownMap.get(itemUUIDString);
            if (cooldownUntil != null) {
                if (System.currentTimeMillis()/1000L <= cooldownUntil) {
                    event.setCancelled(true);
                    return;
                }
            }

            Player p = (Player) event.getEntity();

            Integer paperChaseId;
            if ((paperChaseId = ItemLibrary.getPaperChaseId(stack)) != null) {
                p.sendMessage(ChatColor.RED + "[PaperMC] Found cookie #" + paperChaseId.toString());

                this.pickupCooldownMap.put(itemUUIDString, System.currentTimeMillis()/1000L+2);
                event.setCancelled(true);
            }
        }
    }
}
