package de.kappa.paperchase;

import de.kappa.paperchase.eventlisteners.PickupListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getLogger().info("Starting up");

        // Register EventListeners
        getServer().getPluginManager().registerEvents(new PickupListener(), this);

    }
}
