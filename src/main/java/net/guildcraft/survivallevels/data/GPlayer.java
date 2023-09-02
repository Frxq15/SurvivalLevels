package net.guildcraft.survivallevels.data;

import net.guildcraft.survivallevels.SurvivalLevels;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GPlayer {

    private SurvivalLevels plugin = SurvivalLevels.getInstance();
    private final static Map<UUID, GPlayer> players = new HashMap<>();

    private UUID uuid;
    private int level;
    private int xp;


    public GPlayer(UUID uuid) {
        this.uuid = uuid;
        players.put(uuid, this);
    }
    public static GPlayer getPlayerData(SurvivalLevels plugin, UUID uuid) {
        if (!players.containsKey(uuid)) {
            GPlayer gPlayer = new GPlayer(uuid);
            gPlayer.setLevel(plugin.getSQLSetterGetter().getLevel(uuid));
            gPlayer.setXP(plugin.getSQLSetterGetter().getXP(uuid));
        }
        return players.get(uuid);
    }
    public void setLevel(int level) { this.level = level; }
    public void setXP(int xp) { this.xp = xp; }
    public int getLevel() { return level; }
    public int getXP() { return xp; }
    public void addXP(int xp) { this.xp += xp; }
    public void addLevel(int level) { this.level += level; }
    public void removeXP(int xp) { this.xp -= xp; }
    public void removeLevel(int level) { this.level -= level; }

    public void uploadPlayerData(SurvivalLevels plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.getSQLSetterGetter().updateLevel(uuid, getLevel()));
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.getSQLSetterGetter().updateXP(uuid, getXP()));
    }

    public static Map<UUID, GPlayer> getAllPlayerData() {
        return players;
    }
    public static void removePlayerData(UUID uuid) { players.remove(uuid); }
    public UUID getUUID() { return uuid;}
}
