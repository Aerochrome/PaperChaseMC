package de.kappa.paperchase.libraries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemLibrary {

    public static boolean hasPaperChaseId(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        if (meta.hasLore()) {
            return meta.getLore().contains("paperchase_id");
        }

        return false;
    }

    public static ItemStack createPaperChaseItem() {
        ItemStack stack = new ItemStack(Material.COOKIE);
        ItemMeta meta = stack.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("paperchase_id");

        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }
}
