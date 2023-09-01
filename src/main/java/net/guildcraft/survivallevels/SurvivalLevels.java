package net.guildcraft.survivallevels;

import net.guildcraft.survivallevels.data.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;
import java.util.Locale;

public final class SurvivalLevels extends JavaPlugin {
    private static SurvivalLevels instance;
    private final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private SQLManager sqlManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        log("Plugin enabling...");
        initialize();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        log("Plugin disabled.");
        // Plugin shutdown logic
    }
    public void initialize() {
        sqlManager = new SQLManager(this);
        if (!sqlManager.connect()) {
            log("Failed to connect to MySQL, disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }
    public static SurvivalLevels getInstance() { return instance; }
    public String format(int number) {
        return NUMBER_FORMAT.format(number);
    }
    public void log(String msg) { Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"[SurvivalLevels] "+msg); }
    public String colourize(String msg) { return ChatColor.translateAlternateColorCodes('&', msg); }
    public String formatMsg(String msg) { return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString("MESSAGES."+msg)); }

    public SQLManager getSQLManager() { return sqlManager;}
}
