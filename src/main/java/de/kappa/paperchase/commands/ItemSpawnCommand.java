package de.kappa.paperchase.commands;

import de.kappa.paperchase.Main;
import de.kappa.paperchase.libraries.ItemLibrary;
import de.kappa.paperchase.repositories.ItemRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ItemSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command can only be used ingame");
            return true;
        }

        final Player p = (Player) commandSender;
        ItemStack dropStack = ItemLibrary.createPaperChaseItem();

        final Item droppedItem = p.getWorld().dropItem(p.getLocation(), dropStack);

        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, new Runnable() {
            @Override
            public void run() {
                Integer insertId = null;
                try {
                    insertId = ItemRepository.insertItemStack(
                            droppedItem.getUniqueId(),
                            droppedItem.getWorld().getUID(),
                            droppedItem.getLocation().getX(),
                            droppedItem.getLocation().getY(),
                            droppedItem.getLocation().getZ()
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                    return;
                }

                final Integer fInsertId = insertId;

                Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        droppedItem.setItemStack(ItemLibrary.setPaperChaseId(droppedItem.getItemStack(), fInsertId));
                        p.sendMessage(ChatColor.GREEN + "Spawned paperchase item #" + fInsertId.toString());
                    }
                });

            }
        });

        return true;
    }
}
