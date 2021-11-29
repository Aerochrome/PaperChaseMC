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
            List<String> lore = meta.getLore();
            if (lore == null) return false;
            return hasPaperChaseId(lore);
        }

        return false;
    }

    public static boolean hasPaperChaseId(List<String> lore) {
        return lore.contains("paperchase_id");
    }

    public static boolean isPaperChaseItem(List<String> lore) {
        return lore.contains("paperchase_item");
    }

    public static Integer getPaperChaseId(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        if (hasPaperChaseId(meta)) {
            if (meta.getLore().size() < 3) return null;
            return Integer.parseInt(meta.getLore().get(2));
        }

        return null;
    }

    public static ItemStack createPaperChaseItem() {
        ItemStack stack = new ItemStack(Material.COOKIE);

        ItemMeta meta = stack.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(0, "paperchase_item");

        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }

    public static ItemStack setPaperChaseId(ItemStack stack, Integer id) {
        ItemMeta meta = stack.getItemMeta();

        List<String> lore = meta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
            lore.add(0, "paperchase_item");
        }

        lore.add(1, "paperchase_id");
        lore.add(2, id.toString());

        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }
}
