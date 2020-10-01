package really.stupid.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Vored extends JavaPlugin {
    Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Hello world!");

        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        config.addDefault("family-friendly", false);
        config.addDefault("join-leave", false);
        boolean friendly = config.getBoolean("family-friendly");
        boolean leave = config.getBoolean("join-leave");

        getServer().getPluginManager().registerEvents(new VoredListener(logger, friendly, leave), this);
    }

    @Override
    public void onDisable() {
        logger.info("Goodbye world! *shoots self*");
    }
}
