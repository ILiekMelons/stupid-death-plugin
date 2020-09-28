package really.stupid.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Vored extends JavaPlugin {
    Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Hello world!");
        getServer().getPluginManager().registerEvents(new VoredListener(logger), this);
    }

    @Override
    public void onDisable() {
        logger.info("Goodbye world! *shoots self*");
    }
}
