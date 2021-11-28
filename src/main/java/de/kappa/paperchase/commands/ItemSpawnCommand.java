package de.kappa.paperchase.commands;

import de.kappa.paperchase.libraries.ItemLibrary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command can only be used ingame");
            return true;
        }

        Player p = (Player) commandSender;
        ItemStack dropStack = ItemLibrary.createPaperChaseItem();

        p.getWorld().dropItem(p.getLocation(), dropStack);
        p.sendMessage("Spawned item");
        return true;
    }
}
