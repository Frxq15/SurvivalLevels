package net.guildcraft.survivallevels.level;

import net.guildcraft.survivallevels.SurvivalLevels;
import net.guildcraft.survivallevels.data.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LevelUtils {
    private final SurvivalLevels plugin;

    public LevelUtils(SurvivalLevels plugin) {
        this.plugin = plugin;
    }
    public int getMaxLevel() {
        return plugin.getConfig().getInt("max_level");
    }

    public int getRequiredXP(Player p) {
        GPlayer playerData = GPlayer.getPlayerData(plugin, p.getUniqueId());
        int level = playerData.getLevel();
        if(level < getMaxLevel()) {
            int required = plugin.getConfig().getInt("level_requirements."+(level+1));
            return required;
        }
        return 0;
    }
    public void checkForLevelup(Player p) {
        GPlayer playerData = GPlayer.getPlayerData(plugin, p.getUniqueId());
        int level = playerData.getLevel();
        int xp = playerData.getXP();
        int required = getRequiredXP(p);

        if(level < getMaxLevel()) {
            if(xp >= required) {
                applyLevelup(p, 1);
                checkForLevelup(p);
            }
        }
    }
    public void applyLevelup(Player p, int level) {
        GPlayer playerData = GPlayer.getPlayerData(plugin, p.getUniqueId());
        playerData.addLevel(level);
        for(String commands : plugin.getConfig().getStringList("level_rewards.commands")) {
            commands = commands.replace("%player%", p.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands);
        }
        for(String lines : plugin.getConfig().getStringList("level_rewards.message")) {
            lines = lines.replace("%player%", p.getName()).replace("%level%", level+"");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), lines);
        }
    }
}
