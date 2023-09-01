package net.guildcraft.survivallevels;

import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalLevels extends JavaPlugin {
    private static SurvivalLevels instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static SurvivalLevels getInstance() { return instance; }
}
