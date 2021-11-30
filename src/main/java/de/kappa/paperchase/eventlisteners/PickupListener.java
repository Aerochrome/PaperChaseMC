package de.kappa.paperchase.eventlisteners;

import de.kappa.paperchase.Main;
import de.kappa.paperchase.libraries.ItemLibrary;
import de.kappa.paperchase.repositories.ItemRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
        List<String> itemLore = stack.getItemMeta().getLore();

        if (material == Material.COOKIE) {
            String itemUUIDString = event.getItem().getUniqueId().toString();
            String cooldownIdentifier = itemUUIDString + event.getEntity().getUniqueId().toString();

            if (isInCooldown(cooldownIdentifier)) {
                event.setCancelled(true);
                return;
            }

            final Player p = (Player) event.getEntity();
            final Integer paperChaseId;

            if (itemLore != null && ItemLibrary.isPaperChaseItem(itemLore)) {
                if ((paperChaseId = ItemLibrary.getPaperChaseId(stack)) != null) {

                    Bukkit.getScheduler().runTaskAsynchronously(Main.instance, new Runnable() {
                        @Override
                        public void run() {
                            boolean itemFoundExist = true;
                            try {
                                itemFoundExist = ItemRepository.doesItemFoundExist(p.getUniqueId(), paperChaseId);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return;
                            }

                            String playerMessage;

                            if (!itemFoundExist) {
                                try {
                                    ItemLibrary.saveItemFoundByItemId(paperChaseId, p);
                                    playerMessage = ChatColor.GREEN + "[PaperChase] Found item, congratulations!";
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    playerMessage = ChatColor.RED + "[PaperChase] Ooops. We could not save the item you just found.";
                                }
                            } else {
                                playerMessage = ChatColor.RED + "[PaperChase] You already found that item ...";
                            }

                            final String fplayerMessage = playerMessage;
                            Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                                @Override
                                public void run() {
                                    p.sendMessage(fplayerMessage);
                                }
                            });
                        }
                    });

                    this.pickupCooldownMap.put(cooldownIdentifier, System.currentTimeMillis()/1000L+2);
                }
                event.setCancelled(true);
            }
        }
    }

    private boolean isInCooldown(String cooldownIdentifier) {
        Long cooldownUntil = this.pickupCooldownMap.get(cooldownIdentifier);
        if (cooldownUntil != null) {
            return System.currentTimeMillis() / 1000L <= cooldownUntil;
        }

        return false;
    }
}
