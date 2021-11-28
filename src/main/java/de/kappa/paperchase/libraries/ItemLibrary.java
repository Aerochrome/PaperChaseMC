package de.kappa.paperchase.libraries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemLibrary {

    public static boolean hasPaperChaseId(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        return hasPaperChaseId(meta);
    }

    public static boolean hasPaperChaseId(ItemMeta meta) {
        if (meta.hasLore()) {
            return meta.getLore().contains("paperchase_id");
        }

        return false;
    }

    public static Integer getPaperChaseId(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        if (hasPaperChaseId(meta)) {
            if (meta.getLore().size() < 2) return null;
            return Integer.parseInt(meta.getLore().get(1));
        }

        return null;
    }

    public static ItemStack createPaperChaseItem() {
        return new ItemStack(Material.COOKIE);
    }

    public static ItemStack setPaperChaseId(ItemStack stack, Integer id) {
        ItemMeta meta = stack.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(0, "paperchase_id");
        lore.add(1, id.toString());

        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }
}
