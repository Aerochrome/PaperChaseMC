package de.kappa.paperchase.commands;

import de.kappa.paperchase.libraries.ItemLibrary;
import de.kappa.paperchase.repositories.ItemRepository;
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

        Player p = (Player) commandSender;
        ItemStack dropStack = ItemLibrary.createPaperChaseItem();

        Item droppedItem = p.getWorld().dropItem(p.getLocation(), dropStack);

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
            return false;
        }

        droppedItem.setItemStack(ItemLibrary.setPaperChaseId(droppedItem.getItemStack(), insertId));
        p.sendMessage("Spawned item");
        return true;
    }
}
