package de.kappa.paperchase.services;

import de.kappa.paperchase.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationService {

    public static FileConfiguration config;

    public void updateConfig() {
        Main.instance.saveDefaultConfig();
    }

    public void loadConfig() {
        ConfigurationService.config = Main.instance.getConfig();
    }
}
