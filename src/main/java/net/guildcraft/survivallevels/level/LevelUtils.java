package net.guildcraft.survivallevels.level;

import net.guildcraft.survivallevels.SurvivalLevels;

public class LevelUtils {
    private final SurvivalLevels plugin;

    public LevelUtils(SurvivalLevels plugin) {
        this.plugin = plugin;
    }
    public int getMaxLevel() {
        return plugin.getConfig().getInt("max_level");
    }
}
