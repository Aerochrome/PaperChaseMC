package de.kappa.paperchase.commands;

import de.kappa.paperchase.Main;
import de.kappa.paperchase.libraries.ItemLibrary;
import de.kappa.paperchase.repositories.ItemRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class ItemRemoveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command can only be used in game");
            return true;
        }

        final Player p = (Player) commandSender;

        boolean deletedItem = false;

        for(Entity entity : p.getNearbyEntities(1, 1, 1)) {
            if (!(entity instanceof Item)) {
                continue;
            }

            Item item = (Item) entity;
            ItemStack stack = item.getItemStack();
            List<String> itemLore = stack.getItemMeta().getLore();

            if (!item.getItemStack().getType().equals(Material.COOKIE)) {
                continue;
            }

            if (itemLore != null && ItemLibrary.isPaperChaseItem(itemLore)) {
                final Integer paperChaseId = ItemLibrary.getPaperChaseId(stack);

                if (paperChaseId != null) {
                    Bukkit.getScheduler().runTaskAsynchronously(Main.instance, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Integer deletedFound = ItemRepository.deleteItemFoundByItemId(paperChaseId);
                                Main.instance.getLogger().info("Debug - Deleted item_founds: " + deletedFound.toString());

                                final String playerMessage;

                                if (ItemRepository.deleteItemById(paperChaseId)) {
                                    playerMessage = ChatColor.GREEN + "[PaperChase] Deleted item #" + paperChaseId.toString();
                                } else {
                                    playerMessage = ChatColor.RED + "[PaperChase] Couldn't delete item #" + paperChaseId.toString();
                                }

                                Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                                    @Override
                                    public void run() {
                                        item.remove();
                                        p.sendMessage(playerMessage);
                                    }
                                });
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    deletedItem = true;
                    break;
                }
            }
        }

        if (!deletedItem) {
            p.sendMessage(ChatColor.RED + "[PaperChase] No item nearby");
        }

        return true;
    }
}
