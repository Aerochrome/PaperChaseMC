package de.kappa.paperchase.services;

import de.kappa.paperchase.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ConfigurationService {

    public static FileConfiguration config;

    public void updateConfig() {
        Main.instance.saveDefaultConfig();
    }

    public void loadConfig() {
        ConfigurationService.config = Main.instance.getConfig();
    }

    public static HashMap<String, String> getDatabaseConfigValues() {
        HashMap<String, String> dbConfigValues = new HashMap<String, String>();

        dbConfigValues.put("host", config.getString("db_host", "localhost"));
        dbConfigValues.put("port", config.getString("db_port", "3306"));
        dbConfigValues.put("user", config.getString("db_user", "root"));
        dbConfigValues.put("password", config.getString("db_password", "root"));
        dbConfigValues.put("database", config.getString("db_database", "paperchasemc"));

        return dbConfigValues;
    }

}
