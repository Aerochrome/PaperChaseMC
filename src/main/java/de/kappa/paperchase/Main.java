package de.kappa.paperchase;

import de.kappa.paperchase.eventlisteners.PickupListener;
import de.kappa.paperchase.services.ConfigurationService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin getInstance() {
        return Bukkit.getServer().getPluginManager().getPlugin("PaperChaseMC");
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Starting up");

        this.handleConfiguration();

        // Register EventListeners
        getServer().getPluginManager().registerEvents(new PickupListener(), this);

    }

    private void handleConfiguration() {
        ConfigurationService configService = new ConfigurationService();
        configService.updateConfig();
        configService.loadConfig();
    }
}
