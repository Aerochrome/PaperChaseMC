package de.kappa.paperchase.services;

import de.kappa.paperchase.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationService {

    private final Plugin plugin;
    public static FileConfiguration config;

    public ConfigurationService() {
        this.plugin = Main.getInstance();
    }

    public void updateConfig() {
        this.plugin.saveDefaultConfig();
    }

    public void loadConfig() {
        ConfigurationService.config = this.plugin.getConfig();
    }
}
