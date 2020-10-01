package really.stupid.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Vored extends JavaPlugin {
    Logger logger = getLogger();
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        logger.info("Hello world!");

        config.addDefault("familyFriendlyMode", true);
        boolean friendly = config.getBoolean("familyFriendlyMode");

        getServer().getPluginManager().registerEvents(new VoredListener(logger, friendly), this);
    }

    @Override
    public void onDisable() {
        logger.info("Goodbye world! *shoots self*");
    }
}
