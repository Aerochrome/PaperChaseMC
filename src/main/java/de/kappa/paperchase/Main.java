package de.kappa.paperchase;

import de.kappa.paperchase.commands.ItemRemoveCommand;
import de.kappa.paperchase.commands.ItemSpawnCommand;
import de.kappa.paperchase.eventlisteners.DespawnListener;
import de.kappa.paperchase.eventlisteners.PickupListener;
import de.kappa.paperchase.services.ConfigurationService;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;

        this.getLogger().info("Starting up");

        this.handleConfiguration();

        // Register Commands
        getCommand("pcspawn").setExecutor(new ItemSpawnCommand());
        getCommand("pcremove").setExecutor(new ItemRemoveCommand());

        // Register EventListeners
        getServer().getPluginManager().registerEvents(new PickupListener(), this);
        getServer().getPluginManager().registerEvents(new DespawnListener(), this);

    }

    private void handleConfiguration() {
        ConfigurationService configService = new ConfigurationService();
        configService.updateConfig();
        configService.loadConfig();
    }
}
